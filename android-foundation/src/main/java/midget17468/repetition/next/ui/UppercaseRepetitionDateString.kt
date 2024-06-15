package midget17468.repetition.next.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import midget17468.memo.android_foundation.R
import midget17468.repetition.next.NextRepetitionDate
import midget17468.repetition.next.NextRepetitionDate.DayOfMonth
import midget17468.repetition.next.NextRepetitionDate.Today
import midget17468.repetition.next.NextRepetitionDate.Tomorrow

class UppercaseRepetitionDateString : NextRepetitionDateStrings {

    @Composable
    override fun NextRepetitionDate.string(): String {
        return when (this) {
            is Today -> stringResource(R.string.today)
            is Tomorrow -> stringResource(R.string.tomorrow)
            is DayOfMonth ->
                LocalContext.current
                    .resources.getStringArray(R.array.monthsGenitiveFmt)
                    .get(this.month)
                    .format(this.day)
        }
    }
}