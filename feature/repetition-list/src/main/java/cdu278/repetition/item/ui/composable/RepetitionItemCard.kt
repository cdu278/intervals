package cdu278.repetition.item.ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cdu278.ui.composable.halfMargin

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun RepetitionItemCard(
    colors: CardColors,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    onLongClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    Card(
        colors = colors,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(onClick = onClick, onLongClick = onLongClick)
                .padding(halfMargin)
        ) {
            content()
        }
    }
}