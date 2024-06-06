package springboot.project.component;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springboot.project.entity.Message;
import springboot.project.entity.User;
import springboot.project.model.NotificationRequest;
import springboot.project.service.FcmService;
import springboot.project.utils.DateUtils;

@Component
@RequiredArgsConstructor
public class NotificationJob implements Job {
    private final FcmService fcmService;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String eventName = context.getJobDetail().getJobDataMap().getString("eventName");
        String targetToken = context.getJobDetail().getJobDataMap().getString("targetToken");

        // Tạo thông báo
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTitle("Bạn có lịch hẹn khám");
        notificationRequest.setBody(eventName + " sẽ diễn ra sau 30 phút.");
        notificationRequest.setTargetToken(targetToken);

        // Gửi thông báo
        fcmService.sendNotification(notificationRequest);
    }
}