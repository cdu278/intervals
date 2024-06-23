package cdu278.repetition.new.flow.type.selection.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cdu278.foundation.android.R
import cdu278.repetition.RepetitionType
import cdu278.ui.composable.defaultMargin

@Composable
fun TypeItem(
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
