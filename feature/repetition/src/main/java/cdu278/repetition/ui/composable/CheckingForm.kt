package cdu278.repetition.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.text.input.ImeAction.Companion.None
import androidx.compose.ui.text.input.KeyboardType
import cdu278.intervals.repetition.ui.R
import cdu278.repetition.RepetitionType
import cdu278.repetition.RepetitionType.Email
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.RepetitionType.Phone
import cdu278.repetition.RepetitionType.Pin
import cdu278.repetition.data.ui.composable.emptyRepetitionDataMessage
import cdu278.repetition.ui.UiRepetition
import cdu278.repetition.ui.UiRepetition.State.Checking.Message.DataEmpty
import cdu278.repetition.ui.UiRepetition.State.Checking.Message.Failed
import cdu278.ui.composable.EmailTextField
import cdu278.ui.composable.ErrorText
import cdu278.ui.composable.FlatTextField
import cdu278.ui.composable.TextInput
import cdu278.ui.composable.SecretPasswordField
import cdu278.ui.composable.defaultMargin
import cdu278.repetition.ui.UiRepetition.State.Checking.Message as CheckingMessage

@Composable
internal fun CheckingForm(
    state: UiRepetition.State.Checking,
    type: RepetitionType,
    showHint: () -> Unit,
    check: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        ErrorText(
            text = state.message?.text ?: "",
        )
        Spacer(modifier = Modifier.height(defaultMargin))
        TextInput(text = state.data) {
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(null) {
                focusRequester.requestFocus()
            }
            when (type) {
                Password, Pin ->
                    SecretPasswordField(
                        value,
                        onValueChange,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = when (type) {
                                Password -> KeyboardType.Password
                                Pin -> KeyboardType.NumberPassword
                                else -> throw RuntimeException("Unreachable")
                            },
                            imeAction = if (state.inProgress || !state.valid) None else Done,
                        ),
                        keyboardActions = KeyboardActions(onDone = { check() }),
                        enabled = !state.inProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )

                Email, Phone ->
                    FlatTextField(
                        value,
                        onValueChange,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = when (type) {
                                Email -> KeyboardType.Email
                                Phone -> KeyboardType.Phone
                                else -> error("Unknown type: '$type'")
                            },
                            imeAction = if (state.inProgress || !state.valid) None else Done,
                        ),
                        keyboardActions = KeyboardActions(onDone = { check() }),
                        enabled = !state.inProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
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

private val CheckingMessage.text: String
    @Composable
    get() = when (this) {
        is DataEmpty -> emptyRepetitionDataMessage(this.type)
        is Failed -> stringResource(R.string.repetition_failed)
    }
