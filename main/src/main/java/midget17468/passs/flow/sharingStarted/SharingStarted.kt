package midget17468.passs.flow.sharingStarted

import kotlinx.coroutines.flow.SharingStarted

val uiModelSharingStarted: SharingStarted
    get() = SharingStarted.WhileSubscribed(stopTimeoutMillis = 1_000)
