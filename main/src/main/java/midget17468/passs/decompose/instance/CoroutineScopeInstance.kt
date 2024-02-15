package midget17468.passs.decompose.instance

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

val ComponentContext.retainedCoroutineScope: CoroutineScope
    get() = instanceKeeper
        .getOrCreate("retainedCoroutineScope") { CoroutineScopeInstance() }
        .scope