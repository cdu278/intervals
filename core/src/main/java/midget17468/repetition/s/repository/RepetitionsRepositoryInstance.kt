package midget17468.repetition.s.repository

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import midget17468.repetition.RepetitionQueries
import midget17468.updates.Updates

class RepetitionsRepositoryInstance internal constructor(
    queries: RepetitionQueries,
    updates: Updates,
    private val coroutineScope: CoroutineScope
) : RepetitionsRepository by RepetitionsRepository(queries, updates, coroutineScope),
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