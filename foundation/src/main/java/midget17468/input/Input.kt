package midget17468.input

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Input<T> {

    val value: T

    fun update(transform: (T) -> T)

    fun <R> handle(
        coroutineScope: CoroutineScope,
        initialValue: R,
        flow: (T) -> Flow<R>
    ): StateFlow<R>
}