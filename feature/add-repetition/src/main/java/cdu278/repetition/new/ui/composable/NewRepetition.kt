package cdu278.repetition.new.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cdu278.ui.composable.ErrorText
import cdu278.ui.composable.TextInput
import cdu278.ui.composable.defaultMargin
import cdu278.ui.composable.doubleMargin
import cdu278.intervals.repetition.add.ui.R
import cdu278.repetition.new.data.ui.UiNewRepetitionData
import cdu278.repetition.new.data.ui.composable.NewPasswordData
import cdu278.repetition.new.editor.ui.component.NewRepetitionEditorComponent
import cdu278.repetition.new.ui.UiNewRepetition
import cdu278.repetition.new.ui.UiNewRepetition.Error.EmptyLabel
import cdu278.repetition.new.ui.UiNewRepetition.Error.EmptyPassword
import cdu278.repetition.new.ui.UiNewRepetition.Error.PasswordsDontMatch
import cdu278.foundation.android.R as FoundationR

@Composable
fun NewRepetition(
    component: NewRepetitionEditorComponent,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.large,
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(defaultMargin)
        ) {
            val model by component.uiModelFlow.collectAsState()

            Text(
                text = stringResource(R.string.newRepetition_title),
                style = MaterialTheme.typography.titleMedium,
            )

            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(null) {
                focusRequester.requestFocus()
            }
            TextInput(model.label) {
                OutlinedTextField(
                    value,
                    onValueChange,
                    label = { Text(stringResource(FoundationR.string.label)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .padding(top = doubleMargin)
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )
            }

            when (val data = model.data) {
                is UiNewRepetitionData.Password ->
                    NewPasswordData(
                        data.component,
                        modifier = Modifier
                            .padding(top = defaultMargin)
                            .fillMaxWidth()
                    )
            }

            TextInput(model.hint) {
                OutlinedTextField(
                    value,
                    onValueChange,
                    label = { Text(stringResource(FoundationR.string.hint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = defaultMargin)
                )
            }

            ErrorText(
                text = model.error?.text ?: "",
                modifier = Modifier
                    .padding(top = defaultMargin)
            )

            Button(
                onClick = { component.save() },
                enabled = model.error == null && !model.saving,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(
                        id = if (model.saving) {
                            FoundationR.string.saving
                        } else {
                            FoundationR.string.save
                        }
                    )
                )
            }
        }
    }
}

private val UiNewRepetition.Error.text: String
    @Composable
    get() = stringResource(
        id = when (this) {
            EmptyLabel -> R.string.newRepetition_emptyLabel
            EmptyPassword -> R.string.newRepetition_emptyPassword
            PasswordsDontMatch -> R.string.newRepetition_passwordsDontMatch
        }
    )