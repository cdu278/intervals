package midget17468.memo.compose.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import midget17468.memo.compose.string.UppercaseRepetitionDateString
import midget17468.memo.memo_list.R
import midget17468.memo.model.ui.NextRepetitionDate

@Composable
internal fun NextRepetitionDate(
    nextRepetitionDate: NextRepetitionDate,
    primaryStyle: TextStyle,
    secondaryStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            style = primaryStyle,
            text = stringResource(R.string.memoItem_nextRepetition),
        )
        Text(
            style = secondaryStyle,
            text = with(UppercaseRepetitionDateString()) { nextRepetitionDate.string() },
        )
    }
}