package cdu278.intervals.ui.component.context

import cdu278.computable.Computable
import cdu278.hash.s.Hashes
import cdu278.repetition.notification.s.RepetitionsNotifications
import cdu278.repetition.intervals.RepetitionIntervals
import com.arkivanov.decompose.ComponentContext
import kotlinx.datetime.LocalDateTime

interface IntervalsComponentContext : ComponentContext {

    val hashes: Hashes

    val repetitionIntervals: RepetitionIntervals

    val repetitionNotifications: RepetitionsNotifications

    val currentTime: Computable<LocalDateTime>
}

fun IntervalsComponentContext.newContext(context: ComponentContext): IntervalsComponentContext {
    return object : IntervalsComponentContext,
        ComponentContext by context {

        override val hashes: Hashes
            get() = this@newContext.hashes

        override val repetitionIntervals: RepetitionIntervals
            get() = this@newContext.repetitionIntervals

        override val repetitionNotifications: RepetitionsNotifications
            get() = this@newContext.repetitionNotifications

        override val currentTime: Computable<LocalDateTime>
            get() = this@newContext.currentTime
    }
}