package midget17468.memo.decompose.instance

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import midget17468.memo.MemoQueries
import midget17468.memo.repository.MemoRepository
import midget17468.repository.updates.RepositoryUpdates

class MemoRepositoryInstance internal constructor(
    queries: MemoQueries,
    updates: RepositoryUpdates,
    private val coroutineScope: CoroutineScope
) : MemoRepository by MemoRepository(queries, updates, coroutineScope),
    InstanceKeeper.Instance {

    constructor(
        queries: MemoQueries,
        updates: RepositoryUpdates = RepositoryUpdates(),
    ) : this(
        queries,
        updates,
        CoroutineScope(SupervisorJob())
    )

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}