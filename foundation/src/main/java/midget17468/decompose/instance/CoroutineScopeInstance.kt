package midget17468.decompose.instance

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class CoroutineScopeInstance : InstanceKeeper.Instance {

    val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onDestroy() {
        scope.cancel()
    }
}

val InstanceKeeper.retainedCoroutineScope: CoroutineScope
    get() = getOrCreate("retainedCoroutineScope") { CoroutineScopeInstance() }.scope

val ComponentContext.retainedCoroutineScope: CoroutineScope
    get() = instanceKeeper.retainedCoroutineScope