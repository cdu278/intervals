package cdu278.repetition.notification.worker

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import cdu278.IntervalsApplication
import cdu278.intervals.R
import cdu278.notification.channel.config.RepetitionsChannelConfig
import cdu278.notification.identity.RepetitionsToPassNotificationIdentity
import cdu278.notification.s.AndroidNotifications
import cdu278.permission.ContextRuntimePermission
import cdu278.permission.InstallTimePermission
import cdu278.repetition.RepetitionState
import cdu278.repetition.main.ui.activity.MainActivity
import cdu278.repetition.notification.repository.RoomRepetitionNotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class RepetitionNotificationWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    private val module
        get() = (applicationContext as IntervalsApplication).module

    private val resources
        get() = applicationContext.resources

    private fun notificationMessage(labelsOfRepetitions: List<String>): String {
        return resources.getString(
            R.string.repetitionNotification_messageFmt,
            labelsOfRepetitions.joinToString { "\"$it\"" }
        )
    }

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val notifications =
            AndroidNotifications(
                applicationContext,
                permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextRuntimePermission(
                        applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                } else {
                    InstallTimePermission()
                },
                channelConfig = RepetitionsChannelConfig(applicationContext),
            )

        val repository = RoomRepetitionNotificationRepository(module.db.repetitionNotificationDao)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main.immediate)

        val clock = Clock.System
        val timeZone = TimeZone.currentSystemDefault()

        coroutineScope.launch {
            val now = clock.now().toLocalDateTime(timeZone)
            val labels =
                repository
                    .findAll()
                    .filter { repetition ->
                        (repetition.state as? RepetitionState.Repetition)
                            ?.let { it.date <= now } == true
                    }
                    .map { it.label }
            notifications.show(RepetitionsToPassNotificationIdentity) { builder ->
                builder
                    .setContentText(notificationMessage(labels))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(
                        PendingIntent.getActivity(
                            applicationContext,
                            0,
                            Intent(applicationContext, MainActivity::class.java).apply {
                                flags = FLAG_ACTIVITY_REORDER_TO_FRONT
                            },
                            PendingIntent.FLAG_IMMUTABLE,
                        )
                    )
            }
        }

        return Result.success()
    }
}