package cdu278.repetition.state.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import cdu278.intervals.repetition.list.ui.R
import cdu278.repetition.next.ui.composable.NextRepetitionDate
import cdu278.repetition.state.ui.UiRepetitionState
import cdu278.repetition.state.ui.UiRepetitionState.*

@Composable
internal fun RepetitionState(
    state: UiRepetitionState,
    primaryStyle: TextStyle,
    secondaryStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (state) {
            is Repetition ->
                OutlinedButton(onClick = state.repeat) {
                    Text(stringResource(R.string.repetitionItem_repeat))
                }
            is RepetitionAt ->
                NextRepetitionDate(
                    state.date,
                    primaryStyle,
                    secondaryStyle,
                )
            is Forgotten ->
                OutlinedButton(onClick = state.remember) {
                    Text(stringResource(R.string.repetitionItem_iRemember))
                }
        }
    }
}