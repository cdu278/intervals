package midget17468.notification.s

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import midget17468.notification.channel.config.NotificationChannelConfig
import midget17468.notification.identity.NotificationIdentity
import midget17468.permission.Permission

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
            with(identity) { notificationManager.notify(tag, id, notification) }
        }
    }

    suspend fun cancel(identity: NotificationIdentity) {
        if (permission.isGranted()) {
            with(identity) { notificationManager.cancel(tag, id) }
        }
    }

    class Factory(
        private val context: Context,
        private val notificationManager: NotificationManagerCompat,
        private val permission: Permission,
    ) {

        fun create(config: NotificationChannelConfig): AndroidNotifications {
            return AndroidNotifications(context, notificationManager, permission, config)
        }
    }
}