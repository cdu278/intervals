package midget17468.notification.channel.config

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import midget17468.passs.R

@SuppressLint("NewApi")
@Suppress("FunctionName")
fun RepetitionsChannelConfig(context: Context): NotificationChannelConfig {
    return NotificationChannelConfig(id = "repetitions") { id ->
        NotificationChannel(
            id,
            context.getString(R.string.repetitionNotification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        )
    }
}