package midget17468.repetition.main.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import midget17468.ui.composable.defaultMargin
import midget17468.repetition.list.ui.composable.RepetitionList
import midget17468.repetition.main.ui.component.MainComponent
import midget17468.repetition.new.flow.ui.composable.NewRepetitionFlow

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