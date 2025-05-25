package ucatolica.edu.co.audichek.aplication.usecases.notification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationManager<T> {

    private final List<Notification<T>> notifications = new ArrayList<>();

    public void addNotification(Notification<T> notification) {
        notifications.add(notification);
    }

    public void notify(T massage) {
        for (Notification<T> notification : notifications) {
            notification.sendNotification(massage);
        }
    }
}
