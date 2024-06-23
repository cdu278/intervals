package cdu278.repetition.new.flow.ui.composable

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cdu278.intervals.repetition.add.ui.R
import cdu278.repetition.new.flow.ui.component.NewRepetitionButtonComponent

@Composable
fun NewRepetitionFab(
    component: NewRepetitionButtonComponent,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = component::click,
        modifier = modifier
    ) {
        Icon(
            painterResource(R.drawable.ic_plus),
            contentDescription = null
        )
    }
}