package cdu278.intervals.ui.component.context

import cdu278.intervals.db.IntervalsDb
import com.arkivanov.decompose.ComponentContext
import midget17468.hash.s.Hashes
import midget17468.repetition.notification.s.RepetitionsNotifications
import midget17468.repetition.spaced.SpacedRepetitions

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