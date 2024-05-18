package springboot.project.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.project.model.NotificationRequest;
import springboot.project.service.FcmService;

@Service
@Slf4j
public class FcmServiceImpl implements FcmService {
    @Override
    public void sendNotification(NotificationRequest notificationRequest) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(notificationRequest.getTargetToken())
                .setNotification(Notification.builder()
                        .setTitle(notificationRequest.getTitle())
                        .setBody(notificationRequest.getBody())
                        .build())
                .putAllData(notificationRequest.getData())
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        log.info("Successfully sent message: " + response);
    }
}
