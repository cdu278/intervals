package midget17468.repetition.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext
import midget17468.repetition.Repetition
import midget17468.repetition.RepetitionQueries
import midget17468.repetition.item.RepetitionItem
import midget17468.repetition.RepetitionState
import midget17468.updates.Updates
import java.util.concurrent.ConcurrentHashMap

fun RepetitionRepository(
    queries: RepetitionQueries,
    updates: Updates = Updates(),
    coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob())
): RepetitionRepository {
    return RepetitionRepositoryImpl(queries, coroutineScope, updates)
}

private class RepetitionRepositoryImpl(
    private val queries: RepetitionQueries,
    private val coroutineScope: CoroutineScope,
    private val updates: Updates,
) : RepetitionRepository {

    override val itemsFlow: Flow<List<RepetitionItem>>
            by lazy {
                updates.flow.map {
                    withContext(Dispatchers.IO) {
                        queries.selectItems(::RepetitionItem).executeAsList()
                    }
                }.shareIn(coroutineScope, SharingStarted.Eagerly, replay = 1)
            }

    override suspend fun create(repetition: Repetition): Int {
        val rowId = withContext(Dispatchers.IO) {
            queries.run {
                transactionWithResult {
                    insert(repetition)
                    lastInsertRowId().executeAsOne()
                }
            }
        }
        updates.post()
        return rowId.toInt()
    }

    override suspend fun findById(id: Int): Repetition {
        return withContext(Dispatchers.IO) {
            queries.selectById(id).executeAsOne()
        }
    }

    private val flows = ConcurrentHashMap<Int, SharedFlow<Repetition>>()

    override fun flowById(id: Int): Flow<Repetition> {
        return flows.getOrPut(id) {
            updates.flow.map { findById(id) }
                .shareIn(coroutineScope, SharingStarted.Lazily, replay = 1)
        }
    }

    override suspend fun update(id: Int, updatedState: (Repetition) -> RepetitionState) {
        withContext(Dispatchers.IO) {
            queries.transaction {
                val repetition = queries.selectById(id).executeAsOne()
                queries.update(id = id, repetitionState = updatedState(repetition))
            }
        }
        updates.post()
    }

    override suspend fun delete(id: Int, onCommit: () -> Unit) {
        withContext(Dispatchers.IO) {
            queries.transaction {
                afterCommit {
                    updates.post()
                    onCommit()
                }
                queries.delete(id)
            }
        }
    }
}