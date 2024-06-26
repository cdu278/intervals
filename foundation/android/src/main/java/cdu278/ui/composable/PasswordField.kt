package cdu278.ui.composable

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    fontSize: TextUnit = TextUnit.Unspecified,
    enabled: Boolean = true,
) {
    FlatTextField(
        value,
        onValueChange,
        visualTransformation = PasswordVisualTransformation(mask = '*'),
        fontSize = fontSize,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        enabled = enabled,
        modifier = modifier
            .defaultMinSize(240.dp)
    )
}