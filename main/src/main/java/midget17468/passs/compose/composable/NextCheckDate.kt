package midget17468.passs.compose.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import midget17468.passs.main.R
import midget17468.passs.model.ui.UiPasswordItem
import midget17468.passs.model.ui.UiPasswordItem.NextCheckDate.DayOfMonth
import midget17468.passs.model.ui.UiPasswordItem.NextCheckDate.Today
import midget17468.passs.model.ui.UiPasswordItem.NextCheckDate.Tomorrow

@Composable
fun NextCheckDate(
    nextCheckDate: UiPasswordItem.NextCheckDate,
    primaryStyle: TextStyle,
    secondaryStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            style = primaryStyle,
            text = stringResource(R.string.nextCheckAt),
        )
        Text(
            style = secondaryStyle,
            text = when(nextCheckDate) {
                is Today -> stringResource(R.string.today)
                is Tomorrow -> stringResource(R.string.tomorrow)
                is DayOfMonth -> {
                    LocalContext.current
                        .resources.getStringArray(R.array.monthsGenitiveFmt)
                        .get(nextCheckDate.month)
                        .format(nextCheckDate.day)
                }
            },
        )
    }
}
