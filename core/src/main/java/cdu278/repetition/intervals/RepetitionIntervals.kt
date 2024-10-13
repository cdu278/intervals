package cdu278.repetition.intervals

import cdu278.repetition.intervals.strategy.RepetitionIntervalsStrategy
import cdu278.repetition.stage.RepetitionStage
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class RepetitionIntervals(
    private val strategy: RepetitionIntervalsStrategy = RepetitionIntervalsStrategy(),
    private val clock: Clock = Clock.System,
    private val timeZone: () -> TimeZone = { TimeZone.currentSystemDefault() },
) {

    fun next(stage: RepetitionStage): LocalDateTime {
        return (clock.now() + with(strategy) { stage.space() })
            .toLocalDateTime(timeZone())
    }

    fun progressRatio(stage: RepetitionStage): Double {
        return 1 - (4.0 / (stage.value + 4))
    }
}