package springboot.project.service.impl;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springboot.project.dao.MessageRepository;
import springboot.project.dao.RoomRepository;
import springboot.project.dao.UserRepository;
import springboot.project.entity.Message;
import springboot.project.entity.Room;
import springboot.project.entity.User;
import springboot.project.model.MessageDTO;
import springboot.project.model.NotificationRequest;
import springboot.project.model.UserPrincipal;
import springboot.project.service.FcmService;
import springboot.project.service.MessageService;
import springboot.project.utils.Const;
import springboot.project.utils.DateUtils;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final FcmService fcmService;

    @Override
    public Page<Message> findAllByReceiverIdOrSenderId(Pageable pageable, Integer receiverId, Integer senderId) {
        if (!userRepository.existsById(receiverId))
            throw new RuntimeException("Receiver not existed");

        return messageRepository.findAllByReceiverIdOrSenderId(pageable, receiverId, senderId);
    }

    @Override
    public Page<Message> findAllByRoom(Pageable pageable, String room) {
        return messageRepository.findAllByRoom(pageable, room);
    }

    @Override
    public Message saveMessage(MessageDTO dto) throws FirebaseMessagingException {
        Message message = new Message();
        String room = dto.getSenderId() > dto.getReceiverId()
                ? String.format("%dr%d", dto.getReceiverId(), dto.getSenderId())
                : String.format("%dr%d", dto.getSenderId(), dto.getReceiverId());

        message.setContent(dto.getContent());
        message.setSenderId(dto.getSenderId());
        message.setReceiverId(dto.getReceiverId());
        message.setCreatedDate(DateUtils.now());
        message.setRoom(room);

        message = messageRepository.save(message);
        Room roomEntity = roomRepository.findByRoom(room);

        if (roomEntity == null) {
            User user = userRepository.findById(message.getSenderId().intValue()).get();

            roomEntity = new Room();
            if (user.getRole().getId() == Const.ROLE_ID_USER) {
                roomEntity.setUserId(message.getSenderId().intValue());
                roomEntity.setDoctorId(message.getReceiverId().intValue());
            } else {
                roomEntity.setUserId(message.getReceiverId().intValue());
                roomEntity.setDoctorId(message.getSenderId().intValue());
            }
            roomEntity.setRoom(room);
        }
        roomEntity.setLastMessage(message);
        roomRepository.save(roomEntity);
        sendNotify(message);

        return message;
    }

    @Override
    public Message saveMessage(Message dto) throws FirebaseMessagingException {
        String room = dto.getSenderId() > dto.getReceiverId()
                ? String.format("%dr%d", dto.getReceiverId(), dto.getSenderId())
                : String.format("%dr%d", dto.getSenderId(), dto.getReceiverId());

        dto.setCreatedDate(DateUtils.now());
        dto.setRoom(room);

        Message message = messageRepository.save(dto);
        Room roomEntity = roomRepository.findByRoom(room);

        if (roomEntity == null) {
            User user = userRepository.findById(dto.getSenderId().intValue()).get();

            roomEntity = new Room();
            if (user.getRole().getId() == Const.ROLE_ID_USER) {
                roomEntity.setUserId(dto.getSenderId().intValue());
                roomEntity.setDoctorId(dto.getReceiverId().intValue());
            } else {
                roomEntity.setUserId(dto.getReceiverId().intValue());
                roomEntity.setDoctorId(dto.getSenderId().intValue());
            }
            roomEntity.setRoom(room);
        }

        roomEntity.setLastMessage(message);
        roomRepository.save(roomEntity);
        sendNotify(message);

        return message;
    }

    @Override
    public Page<Room> findAllRecent(Pageable pageable) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userRepository.findById(currentUser.getId()).get();

        return user.getRole().getId() == Const.ROLE_ID_USER
                ? roomRepository.findAllByUserId(pageable, user.getId())
                : roomRepository.findAllByDoctorId(pageable, user.getId());
    }

    private void sendNotify(Message message) throws FirebaseMessagingException {
        User sender = userRepository.findById(message.getSenderId().intValue()).get();
        User receiver = userRepository.findById(message.getReceiverId().intValue()).get();

        NotificationRequest notificationRequest = new NotificationRequest();

        notificationRequest.setBody(message.getContent());
        notificationRequest.setTitle("send a message");
        notificationRequest.getData().put("sender", sender.getName());
        notificationRequest.getData().put("time", DateUtils.sdtf.format(message.getCreatedDate()));
        notificationRequest.setTargetToken(receiver.getDevice().getFirebaseToken());

        fcmService.sendNotification(notificationRequest);
    }
}
