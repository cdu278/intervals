package midget17468.repetition.next.mapping

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayIn
import midget17468.repetition.next.NextRepetitionDate
import midget17468.repetition.next.NextRepetitionDate.*

class NextRepetitionDateMapping(
    private val clock: Clock = Clock.System
) {

    fun LocalDateTime.toUiModel(): NextRepetitionDate {
        val midnightOfNow = clock.todayIn(TimeZone.currentSystemDefault())
        val midnightOfDateTime = date.atTime(0, 0, 0, 0).date
        return when (midnightOfNow.daysUntil(midnightOfDateTime)) {
            0 -> Today
            1 -> Tomorrow
            else ->
                DayOfMonth(
                    month = monthNumber - 1,
                    day = dayOfMonth
                )
        }
    }
}