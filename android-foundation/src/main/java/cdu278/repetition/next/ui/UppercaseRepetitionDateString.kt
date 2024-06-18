package cdu278.repetition.next.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cdu278.foundation.android.R
import cdu278.repetition.next.NextRepetitionDate
import cdu278.repetition.next.NextRepetitionDate.DayOfMonth
import cdu278.repetition.next.NextRepetitionDate.Today
import cdu278.repetition.next.NextRepetitionDate.Tomorrow

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