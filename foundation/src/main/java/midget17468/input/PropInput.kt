package midget17468.input

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

fun <T, Prop> Input<T>.prop(prop: T.() -> Prop, updateProp: T.(Prop) -> T): Input<Prop> {
    return PropInput(original = this, prop, updateProp)
}

class PropInput<T, Prop>(
    private val original: Input<T>,
    private val prop: T.() -> Prop,
    private val updateProp: T.(Prop) -> T
) : Input<Prop> {

    override val value: Prop
        get() = original.value.prop()

    override fun update(transform: (Prop) -> Prop) {
        original.update { current ->
            current.updateProp(
                transform(
                    current.prop()
                )
            )
        }
    }

    override fun <R> handle(
        coroutineScope: CoroutineScope,
        initialValue: R,
        flow: (Prop) -> Flow<R>
    ): StateFlow<R> {
        return original.handle(coroutineScope, initialValue, flow = { flow(it.prop()) })
    }
}