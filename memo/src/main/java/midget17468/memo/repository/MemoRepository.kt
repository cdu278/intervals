package midget17468.memo.repository

import kotlinx.coroutines.flow.Flow
import midget17468.memo.Memo
import midget17468.memo.model.domain.MemoItem
import midget17468.memo.model.domain.RepetitionState

interface MemoRepository {

    val itemsFlow: Flow<List<MemoItem>>

    suspend fun create(memo: Memo): Int

    suspend fun findById(id: Int): Memo

    fun flowById(id: Int): Flow<Memo>

    suspend fun update(id: Int, updatedState: (Memo) -> RepetitionState)

    suspend fun delete(id: Int, onCommit: () -> Unit = { })
}