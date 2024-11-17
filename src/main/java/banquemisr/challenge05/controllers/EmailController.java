package banquemisr.challenge05.controllers;

import banquemisr.challenge05.models.email.EmailDetails;
import banquemisr.challenge05.services.TaskService;
import banquemisr.challenge05.swagger.EmailApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api")
public class EmailController implements EmailApi {

    @Autowired
    TaskService taskService;

    @GetMapping("/sendMail")
    public ResponseEntity
    sendMail() {
        String sendSimpleMail = null;
        try {
            sendSimpleMail = taskService.performUpcomingTasks();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(sendSimpleMail,
                HttpStatus.OK);
    }
}
