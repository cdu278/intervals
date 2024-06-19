package cdu278.notification.channel.config

import android.app.NotificationChannel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

class NotificationChannelConfig(
    val id: String,
    private val channelFactory: (id: String) -> NotificationChannel
) {


    @RequiresApi(Build.VERSION_CODES.O)
    fun NotificationManagerCompat.createNotificationChannel() {
        createNotificationChannel(channelFactory.invoke(id))
    }
}