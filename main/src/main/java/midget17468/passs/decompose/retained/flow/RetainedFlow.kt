package midget17468.passs.decompose.retained.flow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class RetainedFlow<T>(
    coldFlow: Flow<T>,
    private val coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Default)
) : InstanceKeeper.Instance,
    SharedFlow<T> by coldFlow.shareIn(coroutineScope, SharingStarted.Lazily) {

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}

inline fun <reified T : Any> ComponentContext.retainedFlow(
    key: String? = null,
    flow: Flow<T>
): SharedFlow<T> {
    return instanceKeeper.getOrCreate(key = "retainedFlow<${T::class.qualifiedName}>($key)") {
        RetainedFlow(flow)
    }
}