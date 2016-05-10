package com.example.uriel.ordertracker.App.Services.Impl;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uriel.ordertracker.App.Activities.LogInActivity;
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Dto.NotificationDTO;
import com.example.uriel.ordertracker.App.Model.Notification;
import com.example.uriel.ordertracker.App.Model.NotificationsReceiver;
import com.example.uriel.ordertracker.App.Model.SessionInformation;
import com.example.uriel.ordertracker.R;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

public class NotificationsService extends IntentService implements Response.Listener<JSONObject>{

    private NotificationsReceiver notificationsReceiver;

    public NotificationsService() {
        super("notification service");
        this.notificationsReceiver = new NotificationsReceiver();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        this.notificationsReceiver.createSocket(Constants.UDP_PORT);
        byte message[] = new byte[Constants.BUFFER_SIZE];

        DatagramPacket datagramPacket = new DatagramPacket(message, Constants.BUFFER_SIZE);

        while (true) {
            try {
                this.notificationsReceiver.receiveMessage(datagramPacket);
                String data = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), "UTF-8");

                try {
                    Notification notification = new Gson().fromJson(data, Notification.class);
                    this.sendACK(String.valueOf(notification.getId()));
                    this.showNotification(notification);
                } catch (Exception e) {
                    e.getMessage();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void sendACK(final String id) {
        String url = Constants.getACKServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(JsonObjectRequest.Method.DELETE, url, null, this, null) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", SessionInformation.getSessionUsername());
                headers.put("token", SessionInformation.getSessionToken());
                headers.put("id", id);
                return headers;
            }
        };

        Request response = Volley.newRequestQueue(this).add(req);
    }

    public void showNotification(Notification notification) {
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
    }

    public void onDestroy() {
        this.notificationsReceiver.close();
    }

    @Override
    public void onResponse(JSONObject response) {}
}
