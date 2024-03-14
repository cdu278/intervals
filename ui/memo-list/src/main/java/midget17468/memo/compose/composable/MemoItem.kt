package midget17468.memo.compose.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import midget17468.compose.defaultMargin
import midget17468.memo.model.ui.UiMemoItem

@Composable
internal fun MemoItem(
    item: UiMemoItem,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(defaultMargin),
    ) {
        val primaryStyle =
            MaterialTheme.typography
                .bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
        val secondaryStyle =
            MaterialTheme.typography
                .bodySmall.copy(color = MaterialTheme.colorScheme.secondary)
        MemoInfo(
            item.info,
            primaryStyle,
            secondaryStyle,
            modifier = Modifier
                .width(170.dp)
        )
        MemoState(
            item.state,
            repeat = item.repeat,
            primaryStyle,
            secondaryStyle,
        )
    }
}