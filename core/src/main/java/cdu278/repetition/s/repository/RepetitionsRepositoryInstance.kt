package cdu278.repetition.s.repository

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class RepetitionsRepositoryInstance(
    repository: (CoroutineScope) -> RepetitionsRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob()),
) : RepetitionsRepository by repository(coroutineScope),
    InstanceKeeper.Instance {

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}