package cdu278.repetition.new.data.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cdu278.foundation.android.R
import cdu278.repetition.new.data.ui.component.NewPhoneDataComponent
import cdu278.ui.composable.PhoneTextField
import cdu278.ui.composable.TextInput
import cdu278.intervals.repetition.add.ui.R as RepetitionsR

@Composable
fun NewPhoneData(
    component: NewPhoneDataComponent<*>,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val rawType = stringResource(R.string.phone)
        RepetitionDataHint(
            text = stringResource(
                RepetitionsR.string.newRepetition_enterDataFmt,
                remember(rawType) { rawType.lowercase() }
            ),
        )
        val model by component.uiModelFlow.collectAsState()
        TextInput(model.phone) {
            PhoneTextField(
                value,
                onValueChange
            )
        }
    }
}
