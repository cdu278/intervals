package midget17468.memo.compose.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import midget17468.memo.memo_list.R
import midget17468.memo.model.ui.UiMemoItem
import midget17468.memo.model.ui.UiMemoItem.State.*

@Composable
internal fun MemoState(
    state: UiMemoItem.State,
    repeat: () -> Unit,
    primaryStyle: TextStyle,
    secondaryStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (state) {
            is Repetition ->
                Button(onClick = repeat) {
                    Text(stringResource(R.string.memoItem_repeat))
                }
            is RepetitionAt ->
                NextRepetitionDate(
                    state.date,
                    primaryStyle,
                    secondaryStyle,
                )
            is Forgotten ->
                Button(onClick = repeat) {
                    Text(stringResource(R.string.memoItem_iRemember))
                }
        }
    }
}