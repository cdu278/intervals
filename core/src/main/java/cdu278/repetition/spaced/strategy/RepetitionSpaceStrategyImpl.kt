package cdu278.repetition.spaced.strategy

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import cdu278.repetition.stage.RepetitionStage
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun SpaceRepetitionsStrategy(
    clock: Clock = Clock.System,
    timeZone: () -> TimeZone = { TimeZone.currentSystemDefault() },
): SpaceRepetitionsStrategy {
    return SpaceRepetitionsStrategyImpl(clock, timeZone)
}

private class SpaceRepetitionsStrategyImpl(
    private val clock: Clock,
    private val timeZone: () -> TimeZone,
) : SpaceRepetitionsStrategy {

    override fun RepetitionStage.space(): Duration {
        val now = clock.now()
        val timeZone = timeZone()

        val stage = this.value

        val localDate = now.toLocalDateTime(timeZone)
        val days =
            if (localDate.time < LocalTime(hour = 6, minute = 0)) {
                stage
            } else {
                stage + 1
            }.toDuration(DurationUnit.DAYS)

        var repetitionDate =
            (now + days)
                .toLocalDateTime(timeZone)
                .date.atTime(hour = 12, minute = 0)
        repetitionDate =
            if (stage == 0) {
                maxOf(
                    repetitionDate,
                    (now + 8.toDuration(DurationUnit.HOURS))
                        .toLocalDateTime(timeZone)
                )
            } else {
                repetitionDate
            }
        return repetitionDate.toInstant(timeZone) - now
    }
}