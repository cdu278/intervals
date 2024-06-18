package cdu278.intervals.ui.component.context

import cdu278.db.IntervalsDb
import com.arkivanov.decompose.ComponentContext
import cdu278.hash.s.Hashes
import cdu278.repetition.notification.s.RepetitionsNotifications
import cdu278.repetition.spaced.SpacedRepetitions

interface IntervalsComponentContext : ComponentContext {

    val db: IntervalsDb

    val hashes: Hashes

    val spacedRepetitions: SpacedRepetitions

    val repetitionNotifications: RepetitionsNotifications
}

fun IntervalsComponentContext.newContext(context: ComponentContext): IntervalsComponentContext {
    return object : IntervalsComponentContext,
        ComponentContext by context {

        override val db: IntervalsDb
            get() = this@newContext.db

        override val hashes: Hashes
            get() = this@newContext.hashes

        override val spacedRepetitions: SpacedRepetitions
            get() = this@newContext.spacedRepetitions

        override val repetitionNotifications: RepetitionsNotifications
            get() = this@newContext.repetitionNotifications
    }
}