package midget17468.flowable

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SuspendingFlowable<T>(
    private val block: suspend () -> T
) : Flowable<T> {

    override fun invoke(): Flow<T> {
        return flow { emit(block()) }
    }
}