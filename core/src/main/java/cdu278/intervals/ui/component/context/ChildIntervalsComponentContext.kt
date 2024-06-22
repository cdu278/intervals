package cdu278.intervals.ui.component.context

import com.arkivanov.decompose.childContext

fun IntervalsComponentContext.childContext(key: String): IntervalsComponentContext {
    return IntervalsComponentContext(
        childContext(key),
        hashes,
        spacedRepetitions,
        repetitionNotifications,
        currentTime,
    )
}