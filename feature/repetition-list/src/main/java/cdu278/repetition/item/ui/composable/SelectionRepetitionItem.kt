package cdu278.repetition.item.ui.composable

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cdu278.repetition.info.ui.composable.RepetitionInfo
import cdu278.repetition.list.ui.UiRepetitionList.NonEmpty.Mode.Selection.Item as SelectionRepetitionItem

@Composable
internal fun SelectionRepetitionItem(
    item: SelectionRepetitionItem,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    RepetitionItemCard(
        colors = CardDefaults.cardColors(
            containerColor = if (item.selected) {
                Color.Unspecified
            } else {
                MaterialTheme.colorScheme.background
            }
        ),
        onClick = item.toggleSelected,
        modifier = modifier
    ) {
        RepetitionInfo(
            item.info,
            accentColor = accentColor,
        )
    }
}