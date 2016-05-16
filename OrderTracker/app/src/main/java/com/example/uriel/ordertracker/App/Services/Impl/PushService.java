package com.example.uriel.ordertracker.App.Services.Impl;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.example.uriel.ordertracker.App.Activities.LogInActivity;
import com.example.uriel.ordertracker.App.Activities.OrderActivity;
import com.example.uriel.ordertracker.App.Model.Notification;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;
import com.example.uriel.ordertracker.R;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Uriel on 05-May-16.
 */
public class PushService extends IntentService {
    private android.os.Handler handler;
    private IRestService restService;
    private String username;
    private String token;
    private Boolean sendRequest;

    // Must create a default constructor
    public PushService() {
        // Used to name the worker thread, important only for debugging.
        super("push-service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new android.os.Handler();
        restService = new RestService();
        sendRequest = true;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle args = intent.getExtras();
        username = args.getString(RestService.LOGIN_RESPONSE_NAME);
        token = args.getString(RestService.LOGIN_TOKEN);

        while (true){
            final PushService context = this;

            try {
                if(sendRequest){
                    sendRequest = false;
                    restService.getNotifications(username, token, context);
                    Thread.sleep(3000);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // This describes what will happen when service is triggered
    }

    public void showNotification(ArrayList<Notification> notifications) {
        for (final Notification notification : notifications) {
                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(notification.getTitle())
                                .setContentText(notification.getBody())
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                .setVibrate(new long[]{1000, 0, 0, 0, 0})
                                .setLights(Color.BLUE, 3000, 3000)
                                .setAutoCancel(true)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));


                if(notification.getType() == 1){
                    // Creates an explicit intent for an Activity in your app
                    Intent resultIntent = new Intent(getApplicationContext(), LogInActivity.class);
                    resultIntent.putExtra("should_update", true);

                    // The stack builder object will contain an artificial back stack for the
                    // started Activity.
                    // This ensures that navigating backward from the Activity leads out of
                    // your application to the Home screen.
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                    // Adds the back stack for the Intent (but not the Intent itself)
                    stackBuilder.addParentStack(LogInActivity.class);
                    // Adds the Intent that starts the Activity to the top of the stack
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(notification.getId(), mBuilder.build());
                }else {
                    // Creates an explicit intent for an Activity in your app
                    Intent resultIntent = new Intent(getApplicationContext(), OrderActivity.class);
                    resultIntent.putExtra("should_update", true);
                    resultIntent.putExtra("ReadOnly", true);

                    // The stack builder object will contain an artificial back stack for the
                    // started Activity.
                    // This ensures that navigating backward from the Activity leads out of
                    // your application to the Home screen.
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                    // Adds the back stack for the Intent (but not the Intent itself)
                    stackBuilder.addParentStack(OrderActivity.class);
                    // Adds the Intent that starts the Activity to the top of the stack
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(notification.getId(), mBuilder.build());
                }

            }
        sendRequest = true;
    }
}
