package midget17468.decompose.instance

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class SharedFlowInstance<T> internal constructor(
    flow: Flow<T>,
    private val coroutineScope: CoroutineScope,
) : InstanceKeeper.Instance,
    SharedFlow<T> by flow.shareIn(coroutineScope, SharingStarted.Lazily, replay = 1) {

    constructor(flow: Flow<T>) : this(flow, CoroutineScope(Job()))

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}