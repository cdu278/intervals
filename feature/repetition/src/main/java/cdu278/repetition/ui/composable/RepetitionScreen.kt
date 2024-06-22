package cdu278.repetition.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cdu278.intervals.repetition.ui.R
import cdu278.repetition.next.NextRepetitionDate
import cdu278.repetition.next.NextRepetitionDate.Today
import cdu278.repetition.next.NextRepetitionDate.Tomorrow
import cdu278.repetition.next.ui.NextRepetitionDateStrings
import cdu278.repetition.next.ui.UppercaseRepetitionDateString
import cdu278.repetition.ui.UiRepetition.State.Checking
import cdu278.repetition.ui.UiRepetition.State.Forgotten
import cdu278.repetition.ui.UiRepetition.State.RepetitionAt
import cdu278.repetition.ui.component.RepetitionComponent
import cdu278.foundation.android.R as FoundationR
import cdu278.repetition.ui.UiRepetitionDialog.ArchiveConfirmation as DialogArchiveConfirmation

@Composable
fun RepetitionScreen(
    component: RepetitionComponent,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
    ) { paddings ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(paddings)
                .consumeWindowInsets(paddings)
                .fillMaxWidth()
        ) {
            val modelState = component.uiModelFlow.collectAsState()
            val repetition = modelState.value ?: return@Box
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
            ) {
                RepetitionTitle(repetition)

                val buttonWidth = 230.dp
                when (val state = repetition.state) {
                    is Checking -> {
                        CheckingForm(
                            state = state,
                            type = repetition.type,
                            showHint = component::showHint,
                            check = component::check,
                        )
                        CheckingButtons(
                            state = state,
                            buttonWidth = buttonWidth,
                            check = component::check,
                            forget = component::forget,
                        )
                    }

                    is RepetitionAt -> {
                        RepetitionMessage(
                            icon = R.drawable.ic_success,
                            text = with(RepetitionScheduledAtStrings()) { state.date.string() },
                        )

                        Button(
                            onClick = component::close,
                            modifier = Modifier
                                .width(buttonWidth)
                        ) {
                            Text(stringResource(FoundationR.string.gotIt))
                        }
                    }

                    is Forgotten -> {
                        RepetitionMessage(
                            icon = R.drawable.ic_archive_new,
                            text = stringResource(R.string.repetition_markedAsForgotten),
                        )

                        Button(
                            onClick = component::close,
                            modifier = Modifier
                                .width(buttonWidth)
                        ) {
                            Text(stringResource(FoundationR.string.gotIt))
                        }
                    }
                }
            }
            when (val dialog = repetition.dialog ?: return@Box) {
                is DialogArchiveConfirmation ->
                    AlertDialog(
                        onDismissRequest = dialog.dismiss,
                        confirmButton = {
                            TextButton(onClick = dialog.confirm) {
                                Text(stringResource(FoundationR.string.yes))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = dialog.dismiss) {
                                Text(stringResource(FoundationR.string.cancel))
                            }
                        },
                        title = {
                            Text(
                                stringResource(
                                    R.string.repetition_archive_confirmation_dialog_title
                                )
                            )
                        },
                        text = {
                            Text(
                                stringResource(R.string.repetition_archive_confirmation_dialog_text)
                            )
                        },
                    )
            }
        }
    }
}

private class RepetitionScheduledAtStrings : NextRepetitionDateStrings {

    @Composable
    override fun NextRepetitionDate.string(): String {
        return LocalContext.current
            .resources.getString(
                R.string.repetitionScheduledAtFmt,
                when (this) {
                    is Today -> stringResource(FoundationR.string.today).lowercase()
                    is Tomorrow -> stringResource(FoundationR.string.tomorrow).lowercase()
                    else -> with(UppercaseRepetitionDateString()) { string() }
                }
            )
    }
}