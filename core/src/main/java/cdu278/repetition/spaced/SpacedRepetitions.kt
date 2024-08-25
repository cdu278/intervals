package cdu278.repetition.spaced

import cdu278.repetition.spaced.strategy.FakeSpaceRepetitionsStrategy
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import cdu278.repetition.spaced.strategy.SpaceRepetitionsStrategy
import cdu278.repetition.stage.RepetitionStage
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class SpacedRepetitions(
    private val strategy: SpaceRepetitionsStrategy = FakeSpaceRepetitionsStrategy(5.toDuration(DurationUnit.SECONDS)),
    private val clock: Clock = Clock.System,
    private val timeZone: () -> TimeZone = { TimeZone.currentSystemDefault() },
) {

    fun next(stage: RepetitionStage): LocalDateTime {
        return (clock.now() + with(strategy) { stage.space() })
            .toLocalDateTime(timeZone())
    }
}