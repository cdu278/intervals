package midget17468.repetition.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import midget17468.repetition.R
import midget17468.repetition.ui.UiRepetition
import midget17468.repetition.ui.UiRepetition.State.Checking.Mode.Remembering
import midget17468.repetition.ui.UiRepetition.State.Checking.Mode.Repetition
import midget17468.ui.composable.ErrorText
import midget17468.ui.composable.halfMargin

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
        ErrorText(
            text = state.error ?: "",
        )
        Button(
            onClick = check,
            enabled = state.error == null,
            modifier = Modifier
                .padding(top = halfMargin)
                .width(buttonWidth)
        ) {
            Text(stringResource(R.string.repetition_check))
        }
        when (state.mode) {
            Repetition ->
                Button(
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