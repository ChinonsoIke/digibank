package proj.implementations;

import proj.interfaces.NotificationDao;
import proj.interfaces.NotificationService;
import proj.entities.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDao notificationDao;

    @Autowired
    public NotificationServiceImpl(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @Override
    public Notification createNotification(String message, String type, Long userId) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setType(type);
        notification.setUserId(userId);
        notification.setRead(false);
        return notificationDao.save(notification);
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationDao.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationDao.findByUserIdAndReadFalse(userId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationDao.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationDao.save(notification);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationDao.deleteById(notificationId);
    }

    @Override
    public void notifyTransaction(Long userId, String message) {
        createNotification(message, "TRANSACTION", userId);
    }

    @Override
    public void notifyAccountUpdate(Long userId, String message) {
        createNotification(message, "ACCOUNT", userId);
    }

    @Override
    public void notifySystem(Long userId, String message) {
        createNotification(message, "SYSTEM", userId);
    }
} 