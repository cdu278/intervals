package midget17468.flow

import kotlinx.coroutines.flow.SharingStarted

val uiModelSharingStarted: SharingStarted
    get() = SharingStarted.WhileSubscribed(stopTimeoutMillis = 1_000)
