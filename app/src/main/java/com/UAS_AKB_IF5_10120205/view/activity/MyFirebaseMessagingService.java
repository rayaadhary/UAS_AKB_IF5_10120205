package com.UAS_AKB_IF5_10120205.view.activity;

import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("message");

            // Display the notification using the received title and message
            showNotification(title, message);
        }
    }

    private void showNotification(String title, String message) {
        // Code to show the notification, similar to your previous implementation
        Toast.makeText(this, title + ": " + message, Toast.LENGTH_SHORT).show();
    }
}
