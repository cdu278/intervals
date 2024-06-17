package midget17468.repetition.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import midget17468.memo.android_foundation.R
import midget17468.repetition.RepetitionType.Password
import midget17468.repetition.ui.UiRepetition

@Composable
internal fun RepetitionTitle(
    repetition: UiRepetition,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = repetition.label,
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = when (repetition.type) {
                Password -> stringResource(R.string.password)
            },
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}