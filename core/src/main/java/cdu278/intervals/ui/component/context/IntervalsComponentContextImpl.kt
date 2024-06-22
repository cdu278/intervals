package cdu278.intervals.ui.component.context

import cdu278.computable.Computable
import com.arkivanov.decompose.ComponentContext
import cdu278.hash.s.Hashes
import cdu278.repetition.notification.s.RepetitionsNotifications
import cdu278.repetition.spaced.SpacedRepetitions
import kotlinx.datetime.LocalDateTime

fun IntervalsComponentContext(
    context: ComponentContext,
    hashes: Hashes,
    spacedRepetitions: SpacedRepetitions,
    repetitionNotifications: RepetitionsNotifications,
    currentTime: Computable<LocalDateTime>,
): IntervalsComponentContext {
    return IntervalsComponentContextImpl(
        context,
        hashes,
        spacedRepetitions,
        repetitionNotifications,
        currentTime,
    )
}

private class IntervalsComponentContextImpl(
    context: ComponentContext,
    override val hashes: Hashes,
    override val spacedRepetitions: SpacedRepetitions,
    override val repetitionNotifications: RepetitionsNotifications,
    override val currentTime: Computable<LocalDateTime>,
) : IntervalsComponentContext,
    ComponentContext by context