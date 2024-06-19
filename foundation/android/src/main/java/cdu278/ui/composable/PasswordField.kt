package cdu278.ui.composable

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
    TextField(
        value,
        onValueChange,
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(mask = '*'),
        textStyle = TextStyle(
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors()
            .copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                errorContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                unfocusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                disabledTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                errorTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                cursorColor = MaterialTheme.colorScheme.onTertiaryContainer,
            ),
        enabled = enabled,
        modifier = modifier
            .defaultMinSize(minWidth = 240.dp),
    )
}