package cdu278.notification.s

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cdu278.notification.channel.config.NotificationChannelConfig
import cdu278.notification.identity.NotificationIdentity
import cdu278.permission.Permission

class AndroidNotifications(
    private val context: Context,
    private val notificationManager: NotificationManagerCompat,
    val permission: Permission,
    private val channelConfig: NotificationChannelConfig,
) {

    constructor(
        context: Context,
        permission: Permission,
        channelConfig: NotificationChannelConfig,
    ) : this(
        context,
        NotificationManagerCompat.from(context),
        permission,
        channelConfig,
    )

    @SuppressLint("MissingPermission")
    suspend fun show(
        identity: NotificationIdentity,
        config: (NotificationCompat.Builder) -> NotificationCompat.Builder
    ) {
        if (permission.isGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                with(channelConfig) { notificationManager.createNotificationChannel() }
            }
            val notification =
                config(NotificationCompat.Builder(context, channelConfig.id))
                    .build()
            with(identity) { notificationManager.notify(tag, id.toInt(), notification) }
        }
    }

    suspend fun cancel(identity: NotificationIdentity) {
        if (permission.isGranted()) {
            with(identity) { notificationManager.cancel(tag, id.toInt()) }
        }
    }
}