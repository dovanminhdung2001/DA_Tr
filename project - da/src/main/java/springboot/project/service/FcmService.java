package springboot.project.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import springboot.project.model.NotificationRequest;

public interface FcmService {
    public void sendNotification(NotificationRequest notificationRequest) throws FirebaseMessagingException;
}
