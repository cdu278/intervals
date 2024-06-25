package cdu278.repetition.type.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cdu278.foundation.android.R
import cdu278.repetition.RepetitionType
import cdu278.repetition.RepetitionType.*
import cdu278.string.ui.composable.lowercaseStringResource

val RepetitionType.lowercaseText: String
    @Composable
    get() = when (this) {
        Password -> lowercaseStringResource(R.string.password)
        Pin -> stringResource(R.string.pin)
        Email -> lowercaseStringResource(R.string.email)
    }