package midget17468.passs.flowable

import kotlinx.coroutines.flow.Flow

fun interface Flowable<out T> : () -> Flow<T>