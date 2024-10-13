package cdu278.state

import com.arkivanov.decompose.ComponentContext
import kotlinx.serialization.KSerializer

fun <T : Any> ComponentContext.createState(
    serializer: KSerializer<T>,
    initialValue: () -> T,
    key: String = "state",
): State<T> {
    return FlowState(
        initialValue = stateKeeper.consume(key, serializer) ?: initialValue(),
    ).also { state ->
        stateKeeper.register(key, serializer) { state.value }
    }
}

fun <T : Any> ComponentContext.createNullableState(
    serializer: KSerializer<T>,
    initialValue: () -> T? = { null },
    key: String = "state"
): State<T?> {
    return FlowState(
        initialValue = stateKeeper.consume(key, serializer) ?: initialValue()
    ).also { state ->
        stateKeeper.register(key, serializer) { state.value }
    }
}