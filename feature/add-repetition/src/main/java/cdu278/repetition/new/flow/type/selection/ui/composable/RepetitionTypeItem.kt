package cdu278.repetition.new.flow.type.selection.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cdu278.foundation.android.R
import cdu278.repetition.RepetitionType
import cdu278.ui.composable.defaultMargin

@Composable
internal fun NewRepetitionTypeItem(
    type: RepetitionType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Spacer(modifier = Modifier.width(defaultMargin))
        RepetitionIcon(type)
        Text(
            text = stringResource(
                id = when (type) {
                    RepetitionType.Password -> R.string.password
                    RepetitionType.Pin -> R.string.pin
                }
            ),
            modifier = Modifier
                .padding(defaultMargin)
        )
    }
}

@Composable
private fun RepetitionIcon(
    type: RepetitionType,
    modifier: Modifier = Modifier
) {
    Icon(
        painterResource(
            id = when (type) {
                RepetitionType.Password -> R.drawable.ic_password
                RepetitionType.Pin -> R.drawable.ic_pin
            }
        ),
        contentDescription = null,
        modifier = modifier
    )
}