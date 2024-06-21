package cdu278.repetition.s.repository

import cdu278.repetition.item.RepetitionItem
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class RepetitionsRepositoryInstance private constructor(
    private val delegate: RepetitionsRepository,
    private val coroutineScope: CoroutineScope,
) : RepetitionsRepository by delegate,
    InstanceKeeper.Instance {

    constructor(
        repository: (CoroutineScope) -> RepetitionsRepository,
        coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob()),
    ) : this(
        delegate = repository(coroutineScope),
        coroutineScope,
    )

    override val itemsFlow: Flow<List<RepetitionItem>> =
        delegate
            .itemsFlow.shareIn(coroutineScope, SharingStarted.Lazily, replay = 1)

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}