package springboot.project.service.impl;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.project.component.NotificationJob;
import springboot.project.service.NotifyScheduleService;
import springboot.project.utils.DateUtils;

import java.util.Date;

@Service
public class NotifyScheduleServiceImpl implements NotifyScheduleService {
    @Autowired
    private Scheduler scheduler;


    @Override
    public void scheduleNotification(Long eventId, String eventName, Date eventTime, String targetToken) throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(eventId, eventName, targetToken);
        Trigger trigger = buildJobTrigger(jobDetail, new Date(eventTime.getTime() - DateUtils.minutesToTime(30)));

        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void updateNotification(Long eventId, String eventName, Date eventTime, String targetToken) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(eventId.toString());
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
        scheduleNotification(eventId, eventName, eventTime, targetToken);
    }

    @Override
    public void deleteNotification(Long eventId) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(eventId.toString());
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }

    @Override
    public JobDetail buildJobDetail(Long eventId, String eventName, String targetToken) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("eventName", eventName);
        jobDataMap.put("targetToken", targetToken);

        return JobBuilder.newJob(NotificationJob.class)
                .withIdentity(eventId.toString())
                .usingJobData(jobDataMap)
                .build();
    }

    @Override
    public Trigger buildJobTrigger(JobDetail jobDetail, Date startTime) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName() + "_trigger")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
