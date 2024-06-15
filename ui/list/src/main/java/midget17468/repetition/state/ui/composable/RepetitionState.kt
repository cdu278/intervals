package midget17468.repetition.state.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import midget17468.memo.memo_list.R
import midget17468.repetition.item.ui.UiRepetitionItem
import midget17468.repetition.item.ui.UiRepetitionItem.State.*
import midget17468.repetition.next.ui.composable.NextRepetitionDate

@Composable
internal fun RepetitionState(
    state: UiRepetitionItem.State,
    repeat: () -> Unit,
    primaryStyle: TextStyle,
    secondaryStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (state) {
            is Repetition ->
                OutlinedButton(onClick = repeat) {
                    Text(stringResource(R.string.repetitionItem_repeat))
                }
            is RepetitionAt ->
                NextRepetitionDate(
                    state.date,
                    primaryStyle,
                    secondaryStyle,
                )
            is Forgotten ->
                OutlinedButton(onClick = repeat) {
                    Text(stringResource(R.string.repetitionItem_iRemember))
                }
        }
    }
}