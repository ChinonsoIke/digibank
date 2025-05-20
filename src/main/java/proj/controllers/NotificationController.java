package proj.controllers;

import proj.interfaces.NotificationService;
import proj.entities.Notification;
import proj.dtos.ApiResponse;
import proj.dtos.ApiResponseBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ApiResponse<Notification> createNotification(@RequestBody NotificationRequest request) {
        Notification notification = notificationService.createNotification(
            request.getMessage(),
            request.getType(),
            request.getUserId()
        );
        return new ApiResponse<>(notification, "Notification created successfully", "00");
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        return new ApiResponse<>(notifications, "Notifications retrieved successfully", "00");
    }

    @GetMapping("/user/{userId}/unread")
    public ApiResponse<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        return new ApiResponse<>(notifications, "Unread notifications retrieved successfully", "00");
    }

    @PutMapping("/{id}/read")
    public ApiResponseBasic markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return new ApiResponseBasic("Notification marked as read", "00");
    }

    @DeleteMapping("/{id}")
    public ApiResponseBasic deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return new ApiResponseBasic("Notification deleted successfully", "00");
    }

    // Request DTO
    public static class NotificationRequest {
        private String message;
        private String type;
        private Long userId;

        // Getters and Setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
} 