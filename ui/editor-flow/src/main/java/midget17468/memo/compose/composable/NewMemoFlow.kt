package midget17468.memo.compose.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import midget17468.compose.defaultMargin
import midget17468.memo.decompose.component.NewMemoFlowComponent
import midget17468.memo.editor_flow.R
import midget17468.memo.model.ui.UiNewMemoFlowState.AddButton
import midget17468.memo.model.ui.UiNewMemoFlowState.Editor
import midget17468.memo.model.ui.UiNewMemoFlowState.Initial

@Composable
fun NewMemoFlow(
    component: NewMemoFlowComponent,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier,
    ) {
        val state by component.stateFlow.collectAsState()
        when (val s = state) {
            is Initial -> { }
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
                    NewMemo(
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