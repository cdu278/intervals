package cdu278.repetition.root.main.tab.filter

import cdu278.computable.Computable
import cdu278.predicate.Predicate
import cdu278.repetition.RepetitionState
import cdu278.repetition.item.RepetitionItem
import kotlinx.datetime.LocalDateTime
import cdu278.repetition.RepetitionState.Repetition as StateRepetition

@Suppress("FunctionName")
internal fun ActualRepetitionsFilter(
    currentTime: Computable<LocalDateTime>,
): Predicate<RepetitionItem> {
    return Predicate { repetition ->
        (repetition.repetitionState as? StateRepetition)
            ?.date?.let { it <= currentTime() } == true
    }
}

@Suppress("FunctionName")
internal fun ActiveRepetitionsFilter(
    currentTime: Computable<LocalDateTime>,
): Predicate<RepetitionItem> {
    return Predicate { repetition ->
        (repetition.repetitionState as? StateRepetition)
            ?.date?.let { it > currentTime() } == true
    }
}

@Suppress("FunctionName")
internal fun ArchivedRepetitionsFilter(): Predicate<RepetitionItem> {
    return Predicate { repetition ->
        repetition.repetitionState is RepetitionState.Forgotten
    }
}