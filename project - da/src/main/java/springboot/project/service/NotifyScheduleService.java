package springboot.project.service;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.Date;

public interface NotifyScheduleService {
    void scheduleNotification(Long eventId, String eventName, Date eventTime, String targetToken) throws SchedulerException;
    void updateNotification(Long eventId, String eventName, Date eventTime, String targetToken) throws SchedulerException;
    void deleteNotification(Long eventId) throws SchedulerException ;
    JobDetail buildJobDetail(Long eventId, String eventName, String targetToken);
    Trigger buildJobTrigger(JobDetail jobDetail, Date startTime) ;
}
