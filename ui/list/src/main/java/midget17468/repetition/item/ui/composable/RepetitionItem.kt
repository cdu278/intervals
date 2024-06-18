package midget17468.repetition.item.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import midget17468.ui.composable.halfMargin
import midget17468.memo.android_foundation.R
import midget17468.repetition.info.ui.composable.RepetitionInfo
import midget17468.repetition.item.ui.UiRepetitionItem
import midget17468.repetition.state.ui.composable.RepetitionState

@Composable
internal fun RepetitionItem(
    item: UiRepetitionItem,
    modifier: Modifier = Modifier
) {
    val containerColor =
        MaterialTheme.colorScheme.surfaceColorAtElevation(
            if (item.isExpanded) {
                4.dp
            } else {
                0.dp
            }
        )
    Card(
        colors = CardDefaults.cardColors(containerColor),
    ) {
        Column(
            modifier = modifier
                .clickable(onClick = item.expanded.toggle)
        ) {
            ItemContent(item)
            if (item.isExpanded) {
                TextButton(
                    onClick = item.delete,
                    modifier = Modifier
                        .width(100.dp)
                        .padding(end = halfMargin, bottom = halfMargin)
                        .align(Alignment.End)
                ) {
                    Text(stringResource(R.string.delete))
                }
            }
        }
    }
}

@Composable
private fun ItemContent(
    item: UiRepetitionItem,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(halfMargin),
    ) {
        val primaryStyle =
            MaterialTheme.typography
                .bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
        val secondaryStyle =
            MaterialTheme.typography
                .bodySmall.copy(color = MaterialTheme.colorScheme.secondary)
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
            item.repeat,
            primaryStyle,
            secondaryStyle,
            modifier = Modifier
                .width(125.dp)
        )
    }
}