package springboot.project.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.firebase.messaging.FirebaseMessagingException;
import springboot.project.entity.Message;

public interface SocketService {

    public void sendSocketMessage(SocketIOClient senderClient, Message message);
    public void saveMessage(SocketIOClient senderClient, Message message) throws FirebaseMessagingException;
    public void saveInfoMessage(SocketIOClient senderClient, String message);
}
