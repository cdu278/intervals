package cdu278.ui.composable

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun FlatTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    fontSize: TextUnit = TextUnit.Unspecified,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
) {
    TextField(
        value,
        onValueChange,
        singleLine = true,
        visualTransformation = visualTransformation,
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
        modifier = modifier,
    )
}