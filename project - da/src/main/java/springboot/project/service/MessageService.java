package springboot.project.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.Message;
import springboot.project.entity.Room;
import springboot.project.model.MessageDTO;

public interface MessageService {
    Page<Message> findAllByReceiverIdOrSenderId(Pageable pageable, Integer receiverId, Integer senderId);
    Page<Message> findAllByRoom(Pageable pageable, String room);
    Message saveMessage(MessageDTO message) throws FirebaseMessagingException;
    Message saveMessage(Message message) throws FirebaseMessagingException;

    Page<Room> findAllRecent(Pageable pageable);
}
