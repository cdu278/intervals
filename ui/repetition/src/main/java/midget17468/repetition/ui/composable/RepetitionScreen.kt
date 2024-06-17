package midget17468.repetition.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import midget17468.loadable.ui.Loadable.Loaded
import midget17468.loadable.ui.Loadable.Loading
import midget17468.repetition.R
import midget17468.repetition.next.NextRepetitionDate
import midget17468.repetition.next.NextRepetitionDate.Today
import midget17468.repetition.next.NextRepetitionDate.Tomorrow
import midget17468.repetition.next.ui.NextRepetitionDateStrings
import midget17468.repetition.next.ui.UppercaseRepetitionDateString
import midget17468.repetition.ui.UiRepetition.State.Checking
import midget17468.repetition.ui.UiRepetition.State.Forgotten
import midget17468.repetition.ui.UiRepetition.State.RepetitionAt
import midget17468.repetition.ui.component.RepetitionComponent
import midget17468.memo.android_foundation.R as FoundationR

@Composable
fun RepetitionScreen(
    component: RepetitionComponent<*>,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val model by component.uiModelFlow.collectAsState()
        when (val m = model) {
            is Loading -> {}
            is Loaded ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(300.dp)
                ) {
                    val repetition = m.value

                    RepetitionTitle(repetition)

                    val buttonWidth = 230.dp
                    when (val state = repetition.state) {
                        is Checking -> {
                            CheckingForm(
                                state = state,
                                type = repetition.type,
                                showHint = component::showHint,
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