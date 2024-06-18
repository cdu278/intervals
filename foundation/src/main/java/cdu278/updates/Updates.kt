package cdu278.updates

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class Updates(
    private val stateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
) {

    val flow: Flow<*>
        get() = stateFlow

    fun post() {
        stateFlow.update { it + 1 }
    }
}