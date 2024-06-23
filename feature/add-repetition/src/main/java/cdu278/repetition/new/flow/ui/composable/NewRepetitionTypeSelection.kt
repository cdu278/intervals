package cdu278.repetition.new.flow.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cdu278.repetition.new.flow.type.selection.ui.component.NewRepetitionTypeSelectionComponent
import cdu278.repetition.new.flow.type.selection.ui.composable.NewRepetitionTypeSelectionCard

@Composable
fun NewRepetitionTypeSelection(
    component: NewRepetitionTypeSelectionComponent,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .background(NewRepetitionOverlayColor)
            .clickable(
                onClick = component::close,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ),
    ) {
        Spacer(modifier = Modifier.width(100.dp))
        NewRepetitionTypeSelectionCard(
            model = component.model,
            modifier = Modifier
                .width(170.dp)
                .padding(
                    bottom = 125.dp,
                )
        )
    }
}