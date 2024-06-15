package midget17468.repetition.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import midget17468.ui.composable.defaultMargin
import midget17468.repetition.R
import midget17468.repetition.ui.UiRepetition.State.Checking.HintState
import midget17468.repetition.ui.UiRepetition.State.Checking.HintState.Hidden
import midget17468.repetition.ui.UiRepetition.State.Checking.HintState.Shown

@Composable
internal fun RepetitionHint(
    state: HintState,
    showHint: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (state) {
            is Hidden ->
                OutlinedButton(
                    onClick = showHint,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(stringResource(R.string.repetition_showHint))
                }
            is Shown ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = state.text,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(defaultMargin)
                    )
                }
        }
    }
}
