package cdu278.repetition.notification.s

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import cdu278.notification.s.AndroidNotifications
import cdu278.notification.identity.RepetitionNotificationIdentity
import cdu278.repetition.notification.worker.RepetitionNotificationWorker
import cdu278.repetition.notification.worker.RepetitionNotificationWorker.Companion.KEY_REPETITION_ID
import kotlin.time.toJavaDuration

class AndroidRepetitionNotifications(
    private val workManager: WorkManager,
    private val notifications: AndroidNotifications,
    private val clock: Clock,
    private val timeZone: () -> TimeZone,
) : RepetitionsNotifications {

    constructor(
        context: Context,
        notifications: AndroidNotifications,
    ) : this(
        WorkManager.getInstance(context),
        notifications,
        clock = Clock.System,
        timeZone = { TimeZone.currentSystemDefault() },
    )

    private fun workName(id: Long): String = "repetitionNotification(repetitionId=$id)"

    override suspend fun schedule(repetitionId: Long, date: LocalDateTime) {
        if (notifications.permission.isGranted()) {
            val delay = date.toInstant(timeZone()) - clock.now()
            workManager.enqueueUniqueWork(
                workName(repetitionId),
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.Builder(RepetitionNotificationWorker::class.java)
                    .setInputData(
                        Data.Builder()
                            .putLong(KEY_REPETITION_ID, repetitionId)
                            .build()
                    )
                    .setInitialDelay(delay.toJavaDuration())
                    .build()
            )
        }
    }

    override suspend fun remove(repetitionId: Long) {
        workManager.cancelUniqueWork(workName(repetitionId))
        notifications.cancel(RepetitionNotificationIdentity(repetitionId))
    }
}