package midget17468.repetition.spaced

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import midget17468.repetition.spaced.strategy.SpaceRepetitionsStrategy
import midget17468.repetition.stage.RepetitionStage

class SpacedRepetitions(
    private val strategy: SpaceRepetitionsStrategy = SpaceRepetitionsStrategy(),
    private val clock: Clock = Clock.System,
    private val timeZone: () -> TimeZone = { TimeZone.currentSystemDefault() },
) {

    fun next(stage: RepetitionStage): LocalDateTime {
        return (clock.now() + with(strategy) { stage.space() })
            .toLocalDateTime(timeZone())
    }
}