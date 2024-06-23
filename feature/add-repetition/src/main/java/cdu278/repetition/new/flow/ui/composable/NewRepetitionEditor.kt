package cdu278.repetition.new.flow.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cdu278.repetition.new.editor.ui.component.NewRepetitionEditorComponent
import cdu278.repetition.new.ui.composable.NewRepetition
import cdu278.ui.composable.defaultMargin

@Composable
fun NewRepetitionEditor(
    component: NewRepetitionEditorComponent,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .background(NewRepetitionOverlayColor)
            .clickable(
                onClick = component::close,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ),
    ) {
        NewRepetition(
            component,
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