package ucatolica.edu.co.audichek.aplication.usecases.notification;

import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.domain.notificationBuilder.EmailBuilder;

@Service
public class EmailNotificationUseCases implements Notification <EmailBuilder> {

    public EmailNotificationUseCases(){}

    @Override
    public void sendNotification(EmailBuilder Email) {
        throw new BadRequestException("this function is not implemented");
    }
}
