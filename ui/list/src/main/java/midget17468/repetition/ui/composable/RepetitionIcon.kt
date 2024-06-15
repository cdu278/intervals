package midget17468.repetition.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import midget17468.memo.memo_list.R
import midget17468.repetition.RepetitionType

@Composable
fun RepetitionIcon(
    type: RepetitionType,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(percent = 50),
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(
                    id = when (type) {
                        RepetitionType.Password -> R.string.repetitionItem_icon_text_password
                    }
                ),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
            )
        }
    }
}