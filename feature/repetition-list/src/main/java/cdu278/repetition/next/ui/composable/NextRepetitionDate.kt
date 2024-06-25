package cdu278.repetition.next.ui.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import cdu278.intervals.repetition.list.ui.R
import cdu278.repetition.item.ui.composable.RepetitionItemColumn
import cdu278.repetition.next.NextRepetitionDate
import cdu278.repetition.next.ui.UppercaseRepetitionDateString

@Composable
internal fun NextRepetitionDate(
    nextRepetitionDate: NextRepetitionDate,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
) {
    RepetitionItemColumn(
        title = stringResource(R.string.repetitionItem_nextRepetition),
        content = with(UppercaseRepetitionDateString()) { nextRepetitionDate.string() },
        titleColor = titleColor,
        modifier = modifier
    )
}