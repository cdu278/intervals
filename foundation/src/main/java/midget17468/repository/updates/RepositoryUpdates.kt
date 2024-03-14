package midget17468.repository.updates

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RepositoryUpdates(
    private val stateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
) {

    val flow: Flow<*>
        get() = stateFlow

    fun post() {
        stateFlow.update { it + 1 }
    }
}