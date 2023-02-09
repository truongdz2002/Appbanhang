package Utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.appelectronicdevicesalessoftware.ActivityBillPayment;
import com.example.appelectronicdevicesalessoftware.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import Object.Const;
import kotlin.jvm.internal.PropertyReference0Impl;

public class PushNotification {
    private Context context;

    public PushNotification(Context context) {
        this.context = context;
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name_1 = "Notification_1";
            String description_1 = "Notification_1";
            int importance_1 = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel_1 = new NotificationChannel(Const.CHANNEL_ID_1, name_1, importance_1);
            channel_1.setDescription(description_1);

            CharSequence name_2 = "Notification_2";
            String description_2 = "Notification_2";
            int importance_2 = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel_2 = new NotificationChannel(Const.CHANNEL_ID_2, name_2, importance_2);
            channel_2.setDescription(description_2);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if(notificationManager!=null)
            {
                notificationManager.createNotificationChannel(channel_1);
                notificationManager.createNotificationChannel(channel_2);
            }
        }

    }
    public void SendNotification_1(String Title,String Massage)
    {
        Intent resultIntent=new Intent(context, ActivityBillPayment.class);
        TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent=taskStackBuilder.getPendingIntent(GetNotificationId(),PendingIntent.FLAG_UPDATE_CURRENT);



        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String timenow_= sdf.format(new Date());
        Notification notification=new NotificationCompat.Builder(context,Const.CHANNEL_ID_1)
                .setContentTitle(Title)
                .setSmallIcon(R.drawable.icon_endow)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Massage))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();
        NotificationManagerCompat notificationManager=NotificationManagerCompat.from(context);
        if(notificationManager!=null)
        {
            notificationManager.notify(Const.NOTIFICATION_ID_1,notification);
        }
    }
    private int GetNotificationId()
    {
        return (int) new Date().getTime();
    }
    public void SendNotification_2()
    {
        Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.logo);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String timenow_= sdf.format(new Date());
        Notification notification=new NotificationCompat.Builder(context,Const.CHANNEL_ID_2)
                .setContentTitle("Title Notification_object")
                .setContentText("Title Notification_object")
                .setSmallIcon(R.drawable.icon_endow)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Title Notification_object"))
                .setLargeIcon(bitmap)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        NotificationManagerCompat notificationManager=NotificationManagerCompat.from(context);
        if(notificationManager!=null)
        {
            notificationManager.notify(Const.NOTIFICATION_ID_2,notification);
        }
    }
}
