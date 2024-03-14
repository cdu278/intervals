package midget17468.memo.compose.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import midget17468.memo.model.domain.MemoType

@Composable
fun MemoIcon(
    type: MemoType,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(percent = 50),
        color = MaterialTheme.colorScheme.primary,
        content = { },
        modifier = modifier,
    )
}