package midget17468.repetition.repository

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import midget17468.repetition.RepetitionQueries
import midget17468.updates.Updates

class RepetitionRepositoryInstance internal constructor(
    queries: RepetitionQueries,
    updates: Updates,
    private val coroutineScope: CoroutineScope
) : RepetitionRepository by RepetitionRepository(queries, updates, coroutineScope),
    InstanceKeeper.Instance {

    constructor(
        queries: RepetitionQueries,
        updates: Updates = Updates(),
    ) : this(
        queries,
        updates,
        CoroutineScope(SupervisorJob())
    )

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}