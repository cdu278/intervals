package midget17468.memo.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext
import midget17468.memo.Memo
import midget17468.memo.MemoQueries
import midget17468.memo.model.domain.MemoItem
import midget17468.memo.model.domain.RepetitionState
import midget17468.repository.updates.RepositoryUpdates
import java.util.concurrent.ConcurrentHashMap

fun MemoRepository(
    queries: MemoQueries,
    updates: RepositoryUpdates = RepositoryUpdates(),
    coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob())
): MemoRepository {
    return MemoRepositoryImpl(queries, coroutineScope, updates)
}

private class MemoRepositoryImpl(
    private val queries: MemoQueries,
    private val coroutineScope: CoroutineScope,
    private val updates: RepositoryUpdates,
) : MemoRepository {

    override val itemsFlow: Flow<List<MemoItem>>
            by lazy {
                updates.flow.map {
                    withContext(Dispatchers.IO) {
                        queries.selectItems(::MemoItem).executeAsList()
                    }
                }.shareIn(coroutineScope, SharingStarted.Eagerly, replay = 1)
            }

    override suspend fun create(memo: Memo): Int {
        val rowId = withContext(Dispatchers.IO) {
            queries.run {
                transactionWithResult {
                    insert(memo)
                    lastInsertRowId().executeAsOne()
                }
            }
        }
        updates.post()
        return rowId.toInt()
    }

    override suspend fun findById(id: Int): Memo {
        return withContext(Dispatchers.IO) {
            queries.selectById(id).executeAsOne()
        }
    }

    private val flows = ConcurrentHashMap<Int, SharedFlow<Memo>>()

    override fun flowById(id: Int): Flow<Memo> {
        return flows.getOrPut(id) {
            updates.flow.map { findById(id) }
                .shareIn(coroutineScope, SharingStarted.Lazily, replay = 1)
        }
    }

    override suspend fun update(id: Int, updatedState: (Memo) -> RepetitionState) {
        withContext(Dispatchers.IO) {
            queries.transaction {
                val memo = queries.selectById(id).executeAsOne()
                queries.update(id = id, repetitionState = updatedState(memo))
            }
        }
        updates.post()
    }
}