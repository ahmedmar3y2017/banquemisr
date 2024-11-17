package banquemisr.challenge05.services.email;

import banquemisr.challenge05.models.email.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
}
