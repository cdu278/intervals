package cdu278.repetition.new.flow.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cdu278.ui.composable.defaultMargin
import cdu278.intervals.repetition.add.ui.R
import cdu278.repetition.new.flow.type.selection.ui.composable.NewRepetitionTypeSelectionCard
import cdu278.repetition.new.flow.ui.UiNewRepetitionFlowState.AddButton
import cdu278.repetition.new.flow.ui.UiNewRepetitionFlowState.Editor
import cdu278.repetition.new.flow.ui.UiNewRepetitionFlowState.Initial
import cdu278.repetition.new.flow.ui.UiNewRepetitionFlowState.TypeSelection
import cdu278.repetition.new.flow.ui.component.NewRepetitionFlowComponent
import cdu278.repetition.new.ui.composable.NewRepetition

@Composable
fun NewRepetitionFlow(
    component: NewRepetitionFlowComponent,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier,
    ) {
        val state by component.stateFlow.collectAsState()
        when (val s = state) {
            is Initial -> {}
            is AddButton -> {
                FloatingActionButton(
                    onClick = s.component::click,
                    modifier = Modifier
                        .padding(bottom = defaultMargin)
                ) {
                    Icon(
                        painterResource(R.drawable.ic_plus),
                        contentDescription = null
                    )
                }
            }
            is TypeSelection ->
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            onClick = s.component::close,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ),
                ) {
                    Spacer(modifier = Modifier.width(100.dp))
                    NewRepetitionTypeSelectionCard(
                        model = s.component.model,
                        modifier = Modifier
                            .width(150.dp)
                            .padding(
                                bottom = 100.dp,
                            )
                    )
                }
            is Editor -> {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            onClick = s.component::close,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ),
                ) {
                    NewRepetition(
                        s.component,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(defaultMargin)
                            .clickable(
                                onClick = { },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ),
                    )
                }
            }
        }
    }
}