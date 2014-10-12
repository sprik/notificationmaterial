package it.sprik.notificationmaterialdesign;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;


public class MainActivity extends Activity {

    private NotificationManager mNotificationManager;
    private int NOTIFICATION_ID = 1;
    private CheckBox chkhead;
    private CheckBox chkpicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chkhead = (CheckBox) findViewById(R.id.chkHead);
        chkpicture = (CheckBox) findViewById(R.id.chkPicture);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public void onClickNotification(View view) {
        mNotificationManager.notify(NOTIFICATION_ID, createNotification(chkhead.isChecked(), chkpicture.isChecked()));
    }

    Notification createNotification(boolean makeHeadsUpNotification, boolean isBigPicture) {
        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        push.setClass(this, SecondActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                push, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("Sample Notification")
                .setContentText("This is a normal notification.")
                .setContentIntent(fullScreenPendingIntent);

        if(isBigPicture) {
            notificationBuilder
                    .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(
                            getResources(), R.drawable.ic_launcher)))
                    .setContentTitle("Sample Big Picture Notification");
        }

        if (makeHeadsUpNotification) {
            notificationBuilder
                    .setContentText("Heads-Up Notification on Android L or above.")
                    .setFullScreenIntent(fullScreenPendingIntent, true);
        }

        return notificationBuilder.build();
    }


}
