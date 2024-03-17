package midget17468.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class FakeClock(
    private val clock: Clock = Clock.System,
    private val timeZone: () -> TimeZone = { TimeZone.currentSystemDefault() },
    private val date: (LocalDateTime) -> LocalDateTime
) : Clock {

    override fun now(): Instant {
        val timeZone = timeZone()
        val now = clock.now().toLocalDateTime(timeZone)
        return date(now).toInstant(timeZone)
    }
}