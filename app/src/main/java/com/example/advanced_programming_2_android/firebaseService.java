package com.example.advanced_programming_2_android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class firebaseService extends FirebaseMessagingService {
    private static final int NOTIFICATION_ID = 123;
    private static MutableLiveData<NotificationData> notificationLiveData;
    private static firebaseService instance;
    public static synchronized firebaseService getInstance() {
        if (instance == null) {
            instance = new firebaseService();
            notificationLiveData = new MutableLiveData<>();
        }
        return instance;
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // Retrieve the message data
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        
        // Create an intent for the notification's click action
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Create a notification channel (if needed)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ChitChat", "ChitChat", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Update the notificationLiveData with the new data
        NotificationData notificationData = new NotificationData(title, body);
        notificationLiveData.postValue(notificationData);

        // Build the notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "ChitChat")  // Updated channel ID here
                        .setSmallIcon(R.drawable.chit_chat_logo)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        // Show the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public LiveData<NotificationData> getNotificationLiveData() {
        return notificationLiveData;
    }
}
