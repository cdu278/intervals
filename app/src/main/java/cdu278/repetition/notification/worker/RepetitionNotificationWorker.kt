package cdu278.repetition.notification.worker

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import cdu278.repetition.notification.repository.RoomRepetitionNotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import cdu278.IntervalsApplication
import cdu278.activity.dependent.DependentActivity
import cdu278.intervals.R
import cdu278.notification.channel.config.RepetitionsChannelConfig
import cdu278.notification.identity.RepetitionNotificationIdentity
import cdu278.notification.s.AndroidNotifications
import cdu278.permission.ContextRuntimePermission
import cdu278.permission.InstallTimePermission
import cdu278.repetition.Repetition
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.main.ui.activity.MainActivity
import cdu278.repetition.root.ui.ScreenConfig
import cdu278.foundation.android.R as FoundationR

class RepetitionNotificationWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    companion object {

        const val KEY_REPETITION_ID = "repetitionId"
    }

    private val module
        get() = (applicationContext as IntervalsApplication).module

    private val resources
        get() = applicationContext.resources

    private fun notificationMessage(repetition: Repetition): String {
        return resources.getString(
            R.string.repetitionNotification_messageFmt,
            repetition.typeText,
            repetition.label
        )
    }

    private val Repetition.typeText: String
        get() = resources.getString(
            when (type) {
                Password -> FoundationR.string.password
            }
        ).lowercase()

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val repetitionId = inputData.getLong(KEY_REPETITION_ID, -1)

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

        coroutineScope.launch {
            val repetition = repository.findById(repetitionId)
            notifications.show(RepetitionNotificationIdentity(repetitionId)) { builder ->
                builder
                    .setContentText(notificationMessage(repetition))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(
                        PendingIntent.getActivity(
                            applicationContext,
                            0,
                            repetitionIntent(repetitionId).apply {
                                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                            },
                            PendingIntent.FLAG_IMMUTABLE,
                        )
                    )
            }
        }

        return Result.success()
    }

    private fun repetitionIntent(repetitionId: Long): Intent {
        return DependentActivity.intent(
            applicationContext,
            MainActivity::class,
            MainDeps(
                initialStack = listOf(
                    ScreenConfig.Main,
                    ScreenConfig.Repetition(repetitionId)
                )
            )
        )
    }

    @Parcelize
    private class MainDeps(
        override val initialStack: List<ScreenConfig>
    ) : MainActivity.Deps
}