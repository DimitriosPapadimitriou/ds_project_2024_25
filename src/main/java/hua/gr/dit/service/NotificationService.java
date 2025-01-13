package hua.gr.dit.service;

import hua.gr.dit.Entitties.Notification;
import hua.gr.dit.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(String recipient, String message) {
        System.out.println("Sending notification to " + recipient + ": " + message);

        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setStatus("Sent");
        notification.setSentAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}
