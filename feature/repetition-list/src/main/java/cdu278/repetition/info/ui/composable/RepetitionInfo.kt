package cdu278.repetition.info.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cdu278.foundation.android.R
import cdu278.repetition.RepetitionType.Email
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.RepetitionType.Phone
import cdu278.repetition.RepetitionType.Pin
import cdu278.repetition.info.ui.UiRepetitionInfo
import cdu278.repetition.item.ui.composable.RepetitionItemColumn
import cdu278.repetition.ui.composable.RepetitionIcon
import cdu278.ui.composable.defaultMargin

@Composable
internal fun RepetitionInfo(
    info: UiRepetitionInfo,
    accentColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        RepetitionIcon(
            info.type,
            backgroundColor = accentColor,
            modifier = Modifier
                .size(48.dp)
        )
        Spacer(modifier = Modifier.width(defaultMargin))
        RepetitionItemColumn(
            title = info.label,
            titleColor = accentColor,
            content = stringResource(
                when (info.type) {
                    Password -> R.string.password
                    Pin -> R.string.pin
                    Email -> R.string.email
                    Phone -> R.string.phone
                },
            ),
        )
    }
}