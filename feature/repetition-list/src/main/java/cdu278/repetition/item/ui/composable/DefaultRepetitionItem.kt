package cdu278.repetition.item.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cdu278.repetition.info.ui.composable.RepetitionInfo
import cdu278.repetition.state.ui.composable.RepetitionState
import cdu278.ui.composable.halfMargin
import cdu278.repetition.list.ui.UiRepetitionList.NonEmpty.Mode.Default.Item as DefaultRepetitionItem

@Composable
internal fun DefaultRepetitionItem(
    item: DefaultRepetitionItem,
    primaryStyle: TextStyle,
    secondaryStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    RepetitionItemCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        onLongClick = item.select,
        modifier = modifier
    ) {
        RepetitionInfo(
            item.info,
            primaryStyle,
            secondaryStyle,
            modifier = Modifier
                .weight(1f)
                .padding(end = halfMargin)
        )
        RepetitionState(
            item.state,
            primaryStyle,
            secondaryStyle,
            modifier = Modifier
                .width(125.dp)
        )
    }
}