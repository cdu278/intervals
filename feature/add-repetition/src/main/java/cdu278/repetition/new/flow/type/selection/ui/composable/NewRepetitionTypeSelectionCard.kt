package cdu278.repetition.new.flow.type.selection.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cdu278.repetition.new.flow.type.selection.ui.UiRepetitionTypeSelection

@Composable
internal fun NewRepetitionTypeSelectionCard(
    model: UiRepetitionTypeSelection,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Column {
            model.items.forEach { item ->
                TypeItem(
                    type = item.type,
                    onClick = item.choose,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}