package com.app.govnokri.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.app.govnokri.MainActivity;
import com.app.govnokri.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private SharedPreferences preferences;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Message Link: " + remoteMessage.getData().get("link"));

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("link"));
    }

    //This method is only generating push notification

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(String messageTitle, String messageBody, String link) {
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        // The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        CharSequence mName = getString(R.string.channel_id);
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, mName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(mChannel);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("link", link);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.super_icon))
                .setSmallIcon(R.mipmap.super_icon)
                .setContentTitle(messageTitle)
                .setAutoCancel(true)
                .setChannelId(CHANNEL_ID)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentText(messageBody);

        // Set the notification vibrate option
        if (preferences.getBoolean("notifications_new_message_vibrate", true)) {
            notificationBuilder.setVibrate(new long[]{10000, 1000, 1000, 1000, 1000});
        }
        // Set the notification ringtone
        if (preferences.getString("notifications_new_message_ringtone", null) != null) {
            notificationBuilder.setSound(Uri.parse(preferences.getString("notifications_new_message_ringtone", null)));
        } else {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSound(alarmSound);
        }

        // Show only if the notification are enabled
        if (preferences.getBoolean("notifications_new_message", true)) {

            notificationBuilder.setContentIntent(pendingIntent);
            notificationManager.notify(notifyID, notificationBuilder.build());
        }

    }
}