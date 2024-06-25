package cdu278.repetition.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cdu278.foundation.android.R
import cdu278.repetition.RepetitionType.Email
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.RepetitionType.Pin
import cdu278.repetition.ui.UiRepetition

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
                Pin -> stringResource(R.string.pin)
                Email -> stringResource(R.string.email)
            },
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}