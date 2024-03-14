package midget17468.decompose.util

import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.observe
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun <T : Any> Value<T>.asStateFlow(lifecycle: Lifecycle): StateFlow<T> {
    val flow = MutableStateFlow(value)
    observe(lifecycle) { flow.value = it }
    return flow
}