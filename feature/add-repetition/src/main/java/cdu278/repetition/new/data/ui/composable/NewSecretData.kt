package cdu278.repetition.new.data.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import cdu278.intervals.repetition.add.ui.R
import cdu278.repetition.new.data.ui.component.NewPasswordDataComponent
import cdu278.ui.composable.TextInput
import cdu278.ui.composable.SecretPasswordField

@Composable
internal fun NewSecretData(
    component: NewPasswordDataComponent<*>,
    keyboardType: KeyboardType,
    type: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        val model by component.uiModelFlow.collectAsState()

        val keyboardOptions =
            KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next,
            )

        RepetitionDataHint(
            text = stringResource(R.string.newRepetition_enterDataFmt, type)
        )
        TextInput(text = model.password) {
            SecretPasswordField(
                value,
                onValueChange,
                keyboardOptions = keyboardOptions,
            )
        }

        RepetitionDataHint(
            text = stringResource(R.string.newRepetition_enterDataConfirmationFmt, type),
        )
        TextInput(text = model.passwordConfirmation) {
            SecretPasswordField(
                value,
                onValueChange,
                keyboardOptions = keyboardOptions,
            )
        }
    }
}