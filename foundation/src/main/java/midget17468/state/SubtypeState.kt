package midget17468.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KClass
import kotlin.reflect.cast

fun <T : Any, SubT : T> State<T>.subtype(subtype: KClass<SubT>): State<SubT> {
    return SubtypeState(subtype, original = this)
}

class SubtypeState<T : Any, SubT : T>(
    private val subtype: KClass<SubT>,
    private val original: State<T>
) : State<SubT> {

    override val value: SubT
        get() = subtype.cast(original.value)

    override fun update(transform: (SubT) -> SubT) {
        original.update { current ->
            transform(subtype.cast(current))
        }
    }

    override fun <R> handle(
        coroutineScope: CoroutineScope,
        initialValue: R,
        flow: (SubT) -> Flow<R>
    ): StateFlow<R> {
        return original.handle(
            coroutineScope,
            initialValue,
            flow = { flow(subtype.cast(it)) }
        )
    }
}