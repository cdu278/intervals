package cdu278.repetition.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import cdu278.intervals.repetition.ui.R
import cdu278.repetition.ui.UiRepetition
import cdu278.repetition.ui.UiRepetition.State.Checking.Mode.Remembering
import cdu278.repetition.ui.UiRepetition.State.Checking.Mode.Repetition
import cdu278.ui.composable.halfMargin

@Composable
internal fun CheckingButtons(
    state: UiRepetition.State.Checking,
    buttonWidth: Dp,
    check: () -> Unit,
    forget: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(
            onClick = check,
            enabled = state.valid && !state.inProgress,
            modifier = Modifier
                .padding(top = halfMargin)
                .width(buttonWidth)
        ) {
            Text(
                text = stringResource(
                    id = if (state.inProgress) {
                        R.string.repetition_checking
                    } else {
                        R.string.repetition_check
                    }
                )
            )
        }
        when (state.mode) {
            Repetition ->
                OutlinedButton(
                    onClick = forget,
                    modifier = Modifier
                        .width(buttonWidth)
                ) {
                    Text(stringResource(R.string.repetition_iForgot))
                }
            Remembering -> {}
        }
    }
}