package cdu278.flowable.parametrized

import kotlinx.coroutines.flow.Flow

interface ParametrizedFlowable<in P, out T> : (P) -> Flow<T>