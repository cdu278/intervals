package midget17468.memo.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import midget17468.memo.Memo
import midget17468.memo.MemoQueries

class RepetitionNotificationRepository(
    private val queries: MemoQueries,
) {

    suspend fun findById(id: Int): Memo {
        return withContext(Dispatchers.IO) {
            queries.selectById(id).executeAsOne()
        }
    }
}