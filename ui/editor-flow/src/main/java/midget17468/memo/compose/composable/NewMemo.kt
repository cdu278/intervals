package midget17468.memo.compose.composable

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import midget17468.compose.composable.ErrorText
import midget17468.compose.composable.TextInput
import midget17468.compose.defaultMargin
import midget17468.compose.doubleMargin
import midget17468.memo.decompose.component.NewMemoEditorComponent
import midget17468.memo.editor_flow.R
import midget17468.memo.model.ui.UiNewMemoData
import midget17468.memo.android_foundation.R as FoundationR

@Composable
fun NewMemo(
    component: NewMemoEditorComponent<*>,
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
                text = stringResource(R.string.newMemo_title),
                style = MaterialTheme.typography.titleMedium,
            )

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
                )
            }

            when (val data = model.data) {
                is UiNewMemoData.Password ->
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
                text = model.error ?: "",
                modifier = Modifier
                    .padding(top = defaultMargin)
            )

            Button(
                onClick = { component.save() },
                enabled = model.error == null,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(stringResource(FoundationR.string.save))
            }
        }
    }
}
