package cdu278.repetition.new.data.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import cdu278.intervals.repetition.add.ui.R
import cdu278.repetition.new.data.ui.component.NewPasswordDataComponent
import cdu278.ui.composable.TextInput
import cdu278.ui.composable.TextPasswordField

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

        val hintFontSize = 12.sp
        val keyboardOptions =
            KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next,
            )

        Text(
            text = stringResource(R.string.newRepetition_enterSecretFmt, type),
            fontSize = hintFontSize,
        )
        TextInput(text = model.password) {
            TextPasswordField(
                value,
                onValueChange,
                keyboardOptions = keyboardOptions,
            )
        }

        Text(
            text = stringResource(R.string.newRepetition_enterSecretConfirmationFmt, type),
            fontSize = hintFontSize,
        )
        TextInput(text = model.passwordConfirmation) {
            TextPasswordField(
                value,
                onValueChange,
                keyboardOptions = keyboardOptions,
            )
        }
    }
}