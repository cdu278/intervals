package midget17468.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

fun <T> State(initialValue: T): State<T> = FlowState(initialValue)

class FlowState<T>(initialValue: T) : State<T> {

    private val stateFlow = MutableStateFlow(initialValue)

    override val value: T
        get() = stateFlow.value

    override fun update(transform: (T) -> T) {
        stateFlow.update(transform)
    }

    override fun <R> handle(
        coroutineScope: CoroutineScope,
        initialValue: R,
        flow: (T) -> Flow<R>
    ): StateFlow<R> {
        val resultFlow = MutableStateFlow(initialValue)
        coroutineScope.launch {
            var collecting: Job? = null
            stateFlow.collect { input ->
                collecting?.cancel()
                collecting =
                    flow(input)
                        .onEach {
                            yield()
                            resultFlow.value = it
                        }
                        .launchIn(this)
            }
        }
        return resultFlow
    }
}