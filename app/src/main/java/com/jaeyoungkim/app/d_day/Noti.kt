package com.jaeyoungkim.app.d_day

import android.app.Notification
import android.content.Context
import android.app.NotificationManager
import android.app.NotificationChannel
import android.graphics.Color
import androidx.core.content.ContextCompat.getSystemService
import android.os.Build
import android.app.PendingIntent
import android.content.Intent
import android.widget.RemoteViews
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.jaeyoungkim.app.d_day.Activty.ShowActivity


class Noti {
        private var NotificationID = 0
        fun RunNotification(context: Context , notiID : Int , setUp: Boolean, title:String, calMil : Long , dday : Long) {
        NotificationID = notiID
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val mBuilder = NotificationCompat.Builder(context.applicationContext, "notify_$notiID")

        val contentView = RemoteViews(context.packageName, R.layout.noti_layout)
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher)
        val switchIntent = Intent(context,ShowActivity::class.java)
        val pendingSwitchIntent = PendingIntent.getBroadcast(context, 1020, switchIntent, 0)
       // contentView.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent)
        contentView.setTextViewText(R.id.title_tv,title)
        contentView.setTextViewText(R.id.day_tv,Format().dateFormat1(calMil))
        contentView.setTextViewText(R.id.d_day_tv,if (dday!=0L) Format().ddayCheck(dday).toString() else "DAY")
        contentView.setTextViewText(R.id.d_day_sign,if (dday < 0) "+" else "-")
        mBuilder.setSmallIcon(R.drawable.wedding_photo)
        mBuilder.setAutoCancel(false)
        mBuilder.setOngoing(true)
        mBuilder.priority = Notification.PRIORITY_HIGH
        mBuilder.setOnlyAlertOnce(true)
        mBuilder.build().flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH
        mBuilder.setContent(contentView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id"
            val channel = NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager?.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }


        val notification = mBuilder.build()
        notificationManager?.notify(NotificationID, notification)
            if (!setUp) notificationManager?.cancel(NotificationID)
    }
}