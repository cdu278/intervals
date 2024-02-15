package midget17468.passs.compose.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import midget17468.passs.compose.doubleMargin
import midget17468.passs.decompose.component.MainComponent
import midget17468.passs.main.R
import midget17468.passs.model.ui.UiPasswordItem
import midget17468.passs.model.ui.UiPasswordList.Loaded
import midget17468.passs.model.ui.UiPasswordList.Loading

@Composable
fun MainScreen(
    component: MainComponent,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        val model by component.uiModelFlow.collectAsState()
        when (val m = model) {
            is Loading -> { }
            is Loaded -> {
                if (m.items.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(m.items, key = UiPasswordItem::id) {
                            PasswordItem(
                                item = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                } else {
                    Text(
                        text = stringResource(R.string.noItemsYet),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = doubleMargin),
                    )
                }
            }
        }
    }
}