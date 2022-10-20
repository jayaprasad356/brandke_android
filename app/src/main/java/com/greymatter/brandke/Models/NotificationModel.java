package Models;

import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class NotificationModel {

    private String NotificationName;
    private String NotificationDescription;

    public NotificationModel(String notificationName, String notificationDescription) {
        NotificationName = notificationName;
        NotificationDescription = notificationDescription;
    }

    public String getNotificationName() {
        return NotificationName;
    }

    public void setNotificationName(String notificationName) {
        NotificationName = notificationName;
    }

    public String getNotificationDescription() {
        return NotificationDescription;
    }

    public void setNotificationDescription(String notificationDescription) {
        NotificationDescription = notificationDescription;
    }
}
