package midget17468.passs.compose.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import midget17468.memo.compose.composable.MemoList
import midget17468.memo.compose.composable.NewMemoFlow
import midget17468.passs.decompose.component.MainComponent

@Composable
fun MainScreen(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        MemoList(
            component.memoListComponent,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
        NewMemoFlow(
            component.newMemoFlowComponent,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}