/**
 * Exercise 5: Implementing the Decorator Pattern
 * 
 * Scenario: Notification system where notifications can be sent via 
 * multiple channels (Email, SMS) using Decorator Pattern.
 */

// Component Interface
interface Notification {
    void send(String message);
}

// Concrete Component
class BasicNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending basic notification: " + message);
    }
}

// Abstract Decorator
abstract class NotificationDecorator implements Notification {
    protected Notification notification;
    
    public NotificationDecorator(Notification notification) {
        this.notification = notification;
    }
    
    @Override
    public void send(String message) {
        notification.send(message);
    }
}

// Concrete Decorators
class EmailNotification extends NotificationDecorator {
    private String email;
    
    public EmailNotification(Notification notification, String email) {
        super(notification);
        this.email = email;
    }
    
    @Override
    public void send(String message) {
        super.send(message);
        sendEmail(message);
    }
    
    private void sendEmail(String message) {
        System.out.println("Sending email to " + email + ": " + message);
    }
}

class SMSNotification extends NotificationDecorator {
    private String phoneNumber;
    
    public SMSNotification(Notification notification, String phoneNumber) {
        super(notification);
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public void send(String message) {
        super.send(message);
        sendSMS(message);
    }
    
    private void sendSMS(String message) {
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}

class SlackNotification extends NotificationDecorator {
    private String channelId;
    
    public SlackNotification(Notification notification, String channelId) {
        super(notification);
        this.channelId = channelId;
    }
    
    @Override
    public void send(String message) {
        super.send(message);
        sendSlackMessage(message);
    }
    
    private void sendSlackMessage(String message) {
        System.out.println("Sending Slack message to channel " + channelId + ": " + message);
    }
}

class PushNotification extends NotificationDecorator {
    private String deviceId;
    
    public PushNotification(Notification notification, String deviceId) {
        super(notification);
        this.deviceId = deviceId;
    }
    
    @Override
    public void send(String message) {
        super.send(message);
        sendPushNotification(message);
    }
    
    private void sendPushNotification(String message) {
        System.out.println("Sending push notification to device " + deviceId + ": " + message);
    }
}

// Test the Decorator Implementation
public class Exercise5_Decorator {
    public static void main(String[] args) {
        String alertMessage = "System Alert: Server maintenance scheduled at 10 PM";
        
        // Send notification via email only
        System.out.println("=== Email Only ===");
        Notification emailNotif = new EmailNotification(
                new BasicNotification(),
                "admin@company.com"
        );
        emailNotif.send(alertMessage);
        
        // Send notification via SMS only
        System.out.println("\n=== SMS Only ===");
        Notification smsNotif = new SMSNotification(
                new BasicNotification(),
                "+1-555-1234"
        );
        smsNotif.send(alertMessage);
        
        // Send notification via Email and SMS
        System.out.println("\n=== Email + SMS ===");
        Notification emailSmsNotif = new SMSNotification(
                new EmailNotification(
                        new BasicNotification(),
                        "admin@company.com"
                ),
                "+1-555-1234"
        );
        emailSmsNotif.send(alertMessage);
        
        // Send notification via Email, SMS, and Slack
        System.out.println("\n=== Email + SMS + Slack ===");
        Notification multiChannelNotif = new SlackNotification(
                new SMSNotification(
                        new EmailNotification(
                                new BasicNotification(),
                                "admin@company.com"
                        ),
                        "+1-555-1234"
                ),
                "#alerts"
        );
        multiChannelNotif.send(alertMessage);
        
        // Send notification via all channels
        System.out.println("\n=== All Channels ===");
        Notification allChannelsNotif = new PushNotification(
                new SlackNotification(
                        new SMSNotification(
                                new EmailNotification(
                                        new BasicNotification(),
                                        "admin@company.com"
                                ),
                                "+1-555-1234"
                        ),
                        "#alerts"
                ),
                "device-12345"
        );
        allChannelsNotif.send(alertMessage);
    }
}
