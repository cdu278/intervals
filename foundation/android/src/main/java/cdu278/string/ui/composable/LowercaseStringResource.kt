package cdu278.string.ui.composable

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource

@Composable
fun lowercaseStringResource(@StringRes resId: Int): String {
    val raw = stringResource(resId)
    return remember(raw) { raw.lowercase() }
}