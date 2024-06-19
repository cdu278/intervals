package cdu278.repetition.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.text.input.ImeAction.Companion.None
import androidx.compose.ui.text.input.KeyboardType
import cdu278.repetition.RepetitionType
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.ui.UiRepetition
import cdu278.ui.composable.TextInput
import cdu278.ui.composable.TextPasswordField
import cdu278.ui.composable.defaultMargin

@Composable
internal fun CheckingForm(
    state: UiRepetition.State.Checking,
    type: RepetitionType,
    showHint: () -> Unit,
    check: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TextInput(text = state.data) {
            when (type) {
                Password -> {
                    val focusRequester = remember { FocusRequester() }
                    LaunchedEffect(null) {
                        focusRequester.requestFocus()
                    }
                    TextPasswordField(
                        value,
                        onValueChange,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = if (state.inProgress || state.error != null) None else Done,
                        ),
                        keyboardActions = KeyboardActions(onDone = { check() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )
                }
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