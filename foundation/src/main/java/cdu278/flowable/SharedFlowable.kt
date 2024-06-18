package cdu278.flowable

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class SharedFlowable<T>(
    original: Flowable<T>,
    coroutineScope: CoroutineScope,
) : Flowable<T> {

    private val shared =
        original()
            .shareIn(coroutineScope, SharingStarted.Lazily, replay = 1)

    override fun invoke(): Flow<T> = shared
}