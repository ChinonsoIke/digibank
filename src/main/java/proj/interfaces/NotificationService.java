package proj.interfaces;

import proj.entities.Notification;
import java.util.List;

public interface NotificationService {
    Notification createNotification(String message, String type, Long userId);
    List<Notification> getUserNotifications(Long userId);
    List<Notification> getUnreadNotifications(Long userId);
    void markAsRead(Long notificationId);
    void deleteNotification(Long notificationId);
    
    // Convenience methods for common notification types
    void notifyTransaction(Long userId, String message);
    void notifyAccountUpdate(Long userId, String message);
    void notifySystem(Long userId, String message);
} 