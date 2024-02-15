package midget17468.passs.compose.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import midget17468.passs.compose.defaultMargin
import midget17468.passs.model.ui.UiPasswordItem

@Composable
fun PasswordItem(
    item: UiPasswordItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { item.open() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(defaultMargin),
        ) {
            PasswordItemIcon(
                item.type,
                modifier = Modifier
                    .size(48.dp)
            )
            val primaryStyle =
                MaterialTheme.typography
                    .bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
            val secondaryStyle =
                MaterialTheme.typography
                    .bodySmall.copy(color = MaterialTheme.colorScheme.secondary)
            PasswordItemType(
                item.type,
                primaryStyle,
                secondaryStyle,
                modifier = Modifier
                    .width(170.dp)
                    .padding(start = defaultMargin)
            )
            NextCheckDate(
                item.nextCheckDate,
                primaryStyle,
                secondaryStyle,
            )
        }
    }
}
