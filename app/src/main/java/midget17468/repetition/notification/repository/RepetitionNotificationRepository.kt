package midget17468.repetition.notification.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import midget17468.repetition.Repetition
import midget17468.repetition.RepetitionQueries

class RepetitionNotificationRepository(
    private val queries: RepetitionQueries,
) {

    suspend fun findById(id: Int): Repetition {
        return withContext(Dispatchers.IO) {
            queries.selectById(id).executeAsOne()
        }
    }
}