package cdu278.repetition.deletion.dialog.ui.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cdu278.intervals.repetition.list.ui.R
import cdu278.repetition.deletion.dialog.ui.DeletedRepetitionsCount.Multiple
import cdu278.repetition.deletion.dialog.ui.DeletedRepetitionsCount.Single
import cdu278.repetition.deletion.dialog.ui.component.RepetitionsDeletionDialogComponent
import cdu278.foundation.android.R as FoundationR

@Composable
fun RepetitionsDeletionDialog(
    component: RepetitionsDeletionDialogComponent,
    modifier: Modifier = Modifier
) {
    val count = component.deletedCount
    AlertDialog(
        onDismissRequest = component::dismiss,
        confirmButton = {
            TextButton(onClick = component::delete) {
                Text(stringResource(R.string.repetition_deletion_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = component::dismiss) {
                Text(stringResource(FoundationR.string.cancel))
            }
        },
        title = {
            Text(
                stringResource(
                    id = when (count) {
                        is Single -> R.string.repetition_deletion_dialog_title_singular
                        is Multiple -> R.string.repetition_deletion_dialog_title_plural
                    }
                )
            )
        },
        text = {
            Text(
                text = when (count) {
                    is Single -> stringResource(R.string.repetition_deletion_dialog_text_singular)
                    is Multiple ->
                        stringResource(
                            R.string.repetition_deletion_dialog_text_pluralFmt,
                            count.value
                        )
                }
            )
        },
        modifier = modifier
    )
}