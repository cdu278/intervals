package cdu278.state

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow

suspend fun <T> State<T>.collectValues(consume: suspend (T) -> Unit) {
    coroutineScope {
        handle(this, initialValue = Unit) { value ->
            flow {
                consume(value)
                emit(Unit)
            }
        }
    }
}