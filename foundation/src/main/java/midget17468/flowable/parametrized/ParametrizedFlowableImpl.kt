package midget17468.flowable.parametrized

import kotlinx.coroutines.flow.Flow
import midget17468.flowable.Flowable

class ParametrizedFlowableImpl<P, T>(
    private val flowable: (P) -> Flowable<T>
) : ParametrizedFlowable<P, T> {

    override fun invoke(arg: P): Flow<T> {
        return flowable(arg).invoke()
    }
}

fun <P, T> ParametrizedFlowable(flowable: (P) -> Flowable<T>): ParametrizedFlowable<P, T> {
    return ParametrizedFlowableImpl(flowable)
}