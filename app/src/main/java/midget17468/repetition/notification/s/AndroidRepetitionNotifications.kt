package midget17468.repetition.notification.s

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import midget17468.notification.s.AndroidNotifications
import midget17468.notification.identity.RepetitionNotificationIdentity
import midget17468.repetition.notification.worker.RepetitionNotificationWorker
import midget17468.repetition.notification.worker.RepetitionNotificationWorker.Companion.KEY_REPETITION_ID
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

    private fun workName(id: Int): String = "repetitionNotification(repetitionId=$id)"

    override suspend fun schedule(repetitionId: Int, date: LocalDateTime) {
        if (notifications.permission.isGranted()) {
            val delay = date.toInstant(timeZone()) - clock.now()
            workManager.enqueueUniqueWork(
                workName(repetitionId),
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.Builder(RepetitionNotificationWorker::class.java)
                    .setInputData(
                        Data.Builder()
                            .putInt(KEY_REPETITION_ID, repetitionId)
                            .build()
                    )
                    .setInitialDelay(delay.toJavaDuration())
                    .build()
            )
        }
    }

    override suspend fun remove(repetitionId: Int) {
        workManager.cancelUniqueWork(workName(repetitionId))
        notifications.cancel(RepetitionNotificationIdentity(repetitionId))
    }
}