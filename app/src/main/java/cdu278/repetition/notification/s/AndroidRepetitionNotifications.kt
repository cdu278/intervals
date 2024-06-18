package cdu278.repetition.notification.s

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import cdu278.notification.identity.RepetitionsToPassNotificationIdentity
import cdu278.notification.s.AndroidNotifications
import cdu278.repetition.notification.worker.RepetitionNotificationWorker
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
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

    private fun workName(repetitionId: Long) = "repetitionNotification(id=$repetitionId)"

    override suspend fun schedule(repetitionId: Long, date: LocalDateTime) {
        if (notifications.permission.isGranted()) {
            val delay = date.toInstant(timeZone()) - clock.now()
            workManager.enqueueUniqueWork(
                workName(repetitionId),
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.Builder(RepetitionNotificationWorker::class.java)
                    .setInitialDelay(delay.toJavaDuration())
                    .build()
            )
        }
    }

    override suspend fun remove() {
        notifications.cancel(RepetitionsToPassNotificationIdentity)
    }
}