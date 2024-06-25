package cdu278.repetition.data.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cdu278.intervals.core.ui.R
import cdu278.repetition.RepetitionType
import cdu278.repetition.type.ui.composable.lowercaseText

@Composable
fun invalidRepetitionDataMessage(type: RepetitionType): String {
    return stringResource(
        R.string.invalidRepetitionDataMessageFmt,
        type.lowercaseText
    )
}