package midget17468.memo.compose.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import midget17468.compose.defaultMargin
import midget17468.memo.android_foundation.R
import midget17468.memo.model.ui.UiMemoItem
import midget17468.memo.model.domain.MemoType.Password

@Composable
internal fun MemoInfo(
    info: UiMemoItem.Info,
    primaryStyle: TextStyle,
    secondaryStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        MemoIcon(
            info.type,
            modifier = Modifier
                .size(48.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = defaultMargin)
        ) {
            Text(
                text = info.label,
                style = primaryStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(when (info.type) {
                    Password -> R.string.password
                }),
                style = secondaryStyle
            )
        }
    }
}