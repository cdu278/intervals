package midget17468.repetition.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import midget17468.repetition.RepetitionType
import midget17468.repetition.RepetitionType.Password
import midget17468.repetition.ui.UiRepetition
import midget17468.ui.composable.TextInput
import midget17468.ui.composable.TextPasswordField
import midget17468.ui.composable.defaultMargin

@Composable
internal fun CheckingForm(
    state: UiRepetition.State.Checking,
    type: RepetitionType,
    showHint: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TextInput(text = state.data) {
            when (type) {
                Password ->
                    TextPasswordField(
                        value,
                        onValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
            }
        }

        state.hintState?.let {
            RepetitionHint(
                state = it,
                showHint = showHint,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = defaultMargin)
            )
        }
    }
}