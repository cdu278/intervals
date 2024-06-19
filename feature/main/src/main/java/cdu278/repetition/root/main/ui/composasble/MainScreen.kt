package cdu278.repetition.root.main.ui.composasble

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cdu278.ui.composable.defaultMargin
import cdu278.repetition.list.ui.composable.RepetitionList
import cdu278.repetition.root.main.ui.component.MainComponent
import cdu278.repetition.new.flow.ui.composable.NewRepetitionFlow

@Composable
fun MainScreen(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        RepetitionList(
            component.repetitionListComponent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(defaultMargin)
                .align(Alignment.TopCenter)
        )
        NewRepetitionFlow(
            component.newRepetitionFlowComponent,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}