package midget17468.memo.repetitions.notifications

import kotlinx.datetime.LocalDateTime

interface RepetitionsNotifications {

    suspend fun schedule(memoId: Int, date: LocalDateTime)

    suspend fun remove(memoId: Int)
}