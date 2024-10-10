package cdu278.state

import com.arkivanov.decompose.ComponentContext
import kotlinx.serialization.KSerializer

inline fun <T : Any> ComponentContext.createState(
    serializer: KSerializer<T>,
    initialValue: () -> T,
    key: String = "state",
): State<T> {
    return FlowState(
        stateKeeper
            .consume(key, serializer)
            ?: initialValue()
    ).also { state ->
        stateKeeper.register(key, serializer) { state.value }
    }
}