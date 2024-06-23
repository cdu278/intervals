package cdu278.repetition.new.data.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import cdu278.repetition.new.data.ui.component.NewPasswordDataComponent
import cdu278.foundation.android.R as FoundationR

@Composable
internal fun NewPasswordData(
    component: NewPasswordDataComponent<*>,
    modifier: Modifier = Modifier,
) {
    NewSecretData(
        component,
        keyboardType = KeyboardType.Password,
        type = stringResource(FoundationR.string.password),
        modifier = modifier
    )
}