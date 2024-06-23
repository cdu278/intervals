package cdu278.repetition.info.ui.composable

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
import cdu278.ui.composable.defaultMargin
import cdu278.foundation.android.R
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.RepetitionType.Pin
import cdu278.repetition.info.ui.UiRepetitionInfo
import cdu278.repetition.ui.composable.RepetitionIcon

@Composable
internal fun RepetitionInfo(
    info: UiRepetitionInfo,
    primaryStyle: TextStyle,
    secondaryStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        RepetitionIcon(
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
                    Pin -> R.string.pin
                }),
                style = secondaryStyle
            )
        }
    }
}