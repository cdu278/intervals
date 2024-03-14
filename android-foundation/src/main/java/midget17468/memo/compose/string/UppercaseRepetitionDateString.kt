package midget17468.memo.compose.string

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import midget17468.memo.android_foundation.R
import midget17468.memo.model.ui.NextRepetitionDate

class UppercaseRepetitionDateString : NextRepetitionDateStrings {

    @Composable
    override fun NextRepetitionDate.string(): String {
        return when(this) {
            is NextRepetitionDate.Today -> stringResource(R.string.today)
            is NextRepetitionDate.Tomorrow -> stringResource(R.string.tomorrow)
            is NextRepetitionDate.DayOfMonth -> {
                LocalContext.current
                    .resources.getStringArray(R.array.monthsGenitiveFmt)
                    .get(this.month)
                    .format(this.day)
            }
        }
    }
}