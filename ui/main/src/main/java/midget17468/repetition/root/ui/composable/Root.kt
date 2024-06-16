package midget17468.repetition.root.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import midget17468.repetition.root.main.ui.composasble.MainScreen
import midget17468.repetition.root.ui.UiRootScreen
import midget17468.repetition.root.ui.component.RootComponent
import midget17468.repetition.ui.composable.RepetitionScreen

@Composable
fun Root(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        val screen by component.screenFlow.collectAsState()
        when (val s = screen ?: return@Box) {
            is UiRootScreen.Main ->
                MainScreen(
                    s.component,
                    modifier = Modifier
                        .fillMaxSize()
                )
            is UiRootScreen.Repetition ->
                RepetitionScreen(
                    s.component,
                    modifier = Modifier
                        .fillMaxSize()
                )
        }
    }
}