package banquemisr.challenge05.scheduler;

import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.email.EmailDetails;
import banquemisr.challenge05.services.TaskService;
import banquemisr.challenge05.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.util.List;

@Configuration
@EnableScheduling

public class SchedulerTasks {

    @Autowired
    TaskService taskService;

    // every 6 h
    @Scheduled(cron = "0 0 0/6 * * *")
    public void scheduleFixedDelayTask() {

        try {
            taskService.performUpcomingTasks();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
