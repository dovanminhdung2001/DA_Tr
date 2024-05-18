package springboot.project.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.project.entity.Message;
import springboot.project.service.MessageService;
import springboot.project.service.SocketService;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketServiceImpl implements SocketService {
    private final MessageService messageService;
    @Override
    public void sendSocketMessage(SocketIOClient senderClient, Message message) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(message.getRoom()).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent("read_message",
                        message);
            }
        }
    }

    @Override
    public void saveMessage(SocketIOClient senderClient, Message message) throws FirebaseMessagingException {
        Message storedMessage = new Message();

        storedMessage.setContent(message.getContent());
        storedMessage.setReceiverId(message.getReceiverId());
        storedMessage.setSenderId(message.getSenderId());

        storedMessage = messageService.saveMessage(message);

        log.info(storedMessage.toString());
        sendSocketMessage(senderClient, storedMessage);
    }

    @Override
    public void saveInfoMessage(SocketIOClient senderClient, String message) {

    }
}
