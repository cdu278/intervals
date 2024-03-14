package midget17468.compose.context

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.subscribe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

fun ComponentContext.coroutineScope(): CoroutineScope {
    return CoroutineScope(SupervisorJob() + Dispatchers.Default)
        .also { scope ->
            lifecycle.subscribe(onDestroy = { scope.cancel() })
        }
}
