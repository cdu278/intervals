package midget17468.memo.compose.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import midget17468.compose.composable.ErrorText
import midget17468.compose.composable.PasswordField
import midget17468.compose.composable.TextInput
import midget17468.compose.composable.TextPasswordField
import midget17468.compose.defaultMargin
import midget17468.compose.halfMargin
import midget17468.memo.compose.string.NextRepetitionDateStrings
import midget17468.memo.compose.string.UppercaseRepetitionDateString
import midget17468.memo.decompose.component.RepetitionComponent
import midget17468.memo.model.domain.MemoType.Password
import midget17468.memo.model.ui.NextRepetitionDate
import midget17468.memo.model.ui.NextRepetitionDate.Today
import midget17468.memo.model.ui.NextRepetitionDate.Tomorrow
import midget17468.memo.model.ui.UiRepetition.State.Checking
import midget17468.memo.model.ui.UiRepetition.State.Checking.HintState.Hidden
import midget17468.memo.model.ui.UiRepetition.State.Checking.HintState.Shown
import midget17468.memo.model.ui.UiRepetition.State.Checking.Mode.Remembering
import midget17468.memo.model.ui.UiRepetition.State.Checking.Mode.Repetition
import midget17468.memo.model.ui.UiRepetition.State.Forgotten
import midget17468.memo.model.ui.UiRepetition.State.RepetitionAt
import midget17468.model.ui.Loadable
import midget17468.repetition.R
import midget17468.memo.android_foundation.R as FoundationR

@Composable
fun RepetitionScreen(
    component: RepetitionComponent<*>,
    modifier: Modifier = Modifier,
) {
    val model by component.uiModelFlow.collectAsState()
    when (val m = model) {
        is Loadable.Loading -> { }
        is Loadable.Loaded ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = modifier,
            ) {
                val repetition = m.value

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = repetition.label,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    Text(
                        text = when (repetition.type) {
                            Password -> stringResource(FoundationR.string.password)
                        },
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                val buttonWidth = 230.dp
                val infoFontSize = 18.sp
                when (val state = repetition.state) {
                    is Checking -> {
                        Column(
                            modifier = Modifier
                                .width(300.dp)
                        ) {
                            TextInput(text = state.data) {
                                when (repetition.type) {
                                    Password ->
                                        TextPasswordField(
                                            value,
                                            onValueChange,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        )
                                }
                            }

                            state.hintState?.let {
                                RepetitionHint(
                                    state = it,
                                    showHint = component::showHint,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = defaultMargin)
                                )
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            ErrorText(
                                text = state.error ?: "",
                            )
                            Button(
                                onClick = component::check,
                                enabled = state.error == null,
                                modifier = Modifier
                                    .padding(top = halfMargin)
                                    .width(buttonWidth)
                            ) {
                                Text(stringResource(R.string.repetition_check))
                            }
                            when (state.mode) {
                                Repetition ->
                                    Button(
                                        onClick = component::forget,
                                        modifier = Modifier
                                            .width(buttonWidth)
                                    ) {
                                        Text(stringResource(R.string.repetition_iForgot))
                                    }
                                Remembering -> { }
                            }
                        }
                    }
                    is RepetitionAt -> {
                        Text(
                            text = with(RepetitionScheduledAtStrings()) { state.date.string() },
                            fontSize = infoFontSize,
                            textAlign = TextAlign.Center,
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
                        Text(
                            text = stringResource(R.string.repetition_markedAsForgottent),
                            fontSize = infoFontSize,
                            textAlign = TextAlign.Center
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