package midget17468.passs.compose.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import midget17468.passs.main.R
import midget17468.passs.model.domain.PasswordType

@Composable
fun PasswordItemType(
    type: PasswordType,
    primaryStyle: TextStyle,
    secondaryStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = when (type) {
                is PasswordType.Sim -> R.string.main_item_type_sim
                is PasswordType.Password -> R.string.main_item_type_password
            }),
            style = primaryStyle,
        )
        Text(
            text = when (type) {
                is PasswordType.Sim -> type.operator
                is PasswordType.Password -> type.service
            },
            style = secondaryStyle
        )
    }
}
