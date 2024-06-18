package cdu278.intervals.ui.component.context

import cdu278.db.IntervalsDb
import com.arkivanov.decompose.ComponentContext
import cdu278.hash.s.Hashes
import cdu278.repetition.notification.s.RepetitionsNotifications
import cdu278.repetition.spaced.SpacedRepetitions

fun IntervalsComponentContext(
    context: ComponentContext,
    db: IntervalsDb,
    hashes: Hashes,
    spacedRepetitions: SpacedRepetitions,
    repetitionNotifications: RepetitionsNotifications,
): IntervalsComponentContext {
    return IntervalsComponentContextImpl(
        context,
        db,
        hashes,
        spacedRepetitions,
        repetitionNotifications,
    )
}

private class IntervalsComponentContextImpl(
    context: ComponentContext,
    override val db: IntervalsDb,
    override val hashes: Hashes,
    override val spacedRepetitions: SpacedRepetitions,
    override val repetitionNotifications: RepetitionsNotifications,
) : IntervalsComponentContext,
    ComponentContext by context