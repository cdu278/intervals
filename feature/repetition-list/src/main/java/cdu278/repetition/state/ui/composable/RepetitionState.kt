package cdu278.repetition.state.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cdu278.intervals.repetition.list.R
import cdu278.repetition.item.ui.composable.RepetitionItemButton
import cdu278.repetition.next.ui.composable.NextRepetitionDate
import cdu278.repetition.state.ui.UiRepetitionState
import cdu278.repetition.state.ui.UiRepetitionState.Forgotten
import cdu278.repetition.state.ui.UiRepetitionState.Repetition
import cdu278.repetition.state.ui.UiRepetitionState.RepetitionAt
import cdu278.foundation.android.R as FoundationR

@Composable
internal fun RepetitionState(
    state: UiRepetitionState,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
) {
    Box(modifier = modifier) {
        when (state) {
            is Repetition ->
                RepetitionItemButton(
                    icon = FoundationR.drawable.ic_arrow_forward,
                    text = R.string.repetitionItem_repeat,
                    onClick = state.repeat
                )
            is RepetitionAt ->
                NextRepetitionDate(
                    nextRepetitionDate = state.date,
                    titleColor = titleColor,
                )
            is Forgotten ->
                RepetitionItemButton(
                    text = R.string.repetitionItem_iRemember,
                    onClick = state.remember
                )
        }
    }
}