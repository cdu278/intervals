package midget17468.memo.compose.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import midget17468.compose.composable.PasswordField
import midget17468.compose.composable.TextInput
import midget17468.compose.composable.TextPasswordField
import midget17468.memo.decompose.component.NewPasswordDataComponent
import midget17468.memo.editor_flow.R

@Composable
internal fun NewPasswordData(
    component: NewPasswordDataComponent<*>,
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
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
            )

        Text(
            stringResource(R.string.newMemo_enterPassword),
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
            stringResource(R.string.newMemo_enterPasswordConfirmation),
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