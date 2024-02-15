package midget17468.passs.flowable.parametrized

import kotlinx.coroutines.flow.Flow

fun interface ParametrizedFlowable<in P, out T> : (P) -> Flow<T>