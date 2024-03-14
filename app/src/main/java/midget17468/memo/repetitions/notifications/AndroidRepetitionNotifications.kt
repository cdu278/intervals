package midget17468.memo.repetitions.notifications

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import midget17468.memo.work.worker.RepetitionNotificationWorker
import midget17468.memo.work.worker.RepetitionNotificationWorker.Companion.KEY_MEMO_ID
import midget17468.notification.AndroidNotifications
import midget17468.notification.identity.RepetitionNotificationIdentity
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

    override suspend fun schedule(memoId: Int, date: LocalDateTime) {
        if (notifications.permission.isGranted()) {
            val delay = date.toInstant(timeZone()) - clock.now()
            workManager.enqueue(
                OneTimeWorkRequest.Builder(RepetitionNotificationWorker::class.java)
                    .setInputData(
                        Data.Builder()
                            .putInt(KEY_MEMO_ID, memoId)
                            .build()
                    )
                    .setInitialDelay(delay.toJavaDuration())
                    .build()
            )
        }
    }

    override suspend fun remove(memoId: Int) {
        notifications.cancel(RepetitionNotificationIdentity(memoId))
    }
}