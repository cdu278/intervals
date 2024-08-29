package cdu278.intervals.ui.component.context

import cdu278.computable.Computable
import com.arkivanov.decompose.ComponentContext
import cdu278.hash.s.Hashes
import cdu278.repetition.notification.s.RepetitionsNotifications
import cdu278.repetition.intervals.RepetitionIntervals
import kotlinx.datetime.LocalDateTime

fun IntervalsComponentContext(
    context: ComponentContext,
    hashes: Hashes,
    repetitionIntervals: RepetitionIntervals,
    repetitionNotifications: RepetitionsNotifications,
    currentTime: Computable<LocalDateTime>,
): IntervalsComponentContext {
    return IntervalsComponentContextImpl(
        context,
        hashes,
        repetitionIntervals,
        repetitionNotifications,
        currentTime,
    )
}

private class IntervalsComponentContextImpl(
    context: ComponentContext,
    override val hashes: Hashes,
    override val repetitionIntervals: RepetitionIntervals,
    override val repetitionNotifications: RepetitionsNotifications,
    override val currentTime: Computable<LocalDateTime>,
) : IntervalsComponentContext,
    ComponentContext by context