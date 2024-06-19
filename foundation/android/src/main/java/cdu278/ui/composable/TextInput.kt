package cdu278.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.drop
import cdu278.ui.input.UiInput

class TextInputScope(initialValue: String) {

    private val valueState = mutableStateOf(initialValue)

    val value: String by valueState

    val onValueChange: (String) -> Unit
        get() = valueState.component2()
}

@Composable
fun TextInput(
    text: UiInput<String>,
    field: @Composable TextInputScope.() -> Unit
) {
    val scope = remember(text) { TextInputScope(text.value) }
    LaunchedEffect(text) {
        snapshotFlow { scope.value }
            .drop(1)
            .collect { text.change(it) }
    }
    scope.field()
}