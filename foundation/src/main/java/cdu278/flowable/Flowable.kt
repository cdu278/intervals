package cdu278.flowable

import kotlinx.coroutines.flow.Flow

fun interface Flowable<out T> : () -> Flow<T>