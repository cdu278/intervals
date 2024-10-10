package cdu278.repetition.intervals

import cdu278.repetition.intervals.strategy.FakeRepetitionIntervalsStrategy
import cdu278.repetition.intervals.strategy.RepetitionIntervalsStrategy
import cdu278.repetition.stage.RepetitionStage
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

class RepetitionIntervals(
    private val strategy: RepetitionIntervalsStrategy = FakeRepetitionIntervalsStrategy(5.seconds),
    private val clock: Clock = Clock.System,
    private val timeZone: () -> TimeZone = { TimeZone.currentSystemDefault() },
) {

    fun next(stage: RepetitionStage): LocalDateTime {
        return (clock.now() + with(strategy) { stage.space() })
            .toLocalDateTime(timeZone())
    }
}