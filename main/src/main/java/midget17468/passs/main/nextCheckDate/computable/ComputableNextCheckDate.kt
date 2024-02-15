package midget17468.passs.main.nextCheckDate.computable

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime
import kotlinx.datetime.daysUntil
import midget17468.passs.computable.Computable
import midget17468.passs.computable.parametrized.ParametrizedComputable
import midget17468.passs.model.ui.UiPasswordItem
import midget17468.passs.model.ui.UiPasswordItem.NextCheckDate.DayOfMonth
import midget17468.passs.model.ui.UiPasswordItem.NextCheckDate.Today
import midget17468.passs.model.ui.UiPasswordItem.NextCheckDate.Tomorrow

class ComputableNextCheckDate(
    private val now: Computable<LocalDateTime>,
) : ParametrizedComputable<LocalDateTime, UiPasswordItem.NextCheckDate> {

    override operator fun invoke(dateTime: LocalDateTime): UiPasswordItem.NextCheckDate {
        val midnightOfNow = now().date.atTime(0, 0, 0, 0).date
        val midnightOfDateTime = dateTime.date.atTime(0, 0, 0, 0).date
        return when (midnightOfNow.daysUntil(midnightOfDateTime)) {
            0 -> Today
            1 -> Tomorrow
            else -> DayOfMonth(
                month = dateTime.monthNumber - 1,
                day = dateTime.dayOfMonth
            )
        }
    }
}