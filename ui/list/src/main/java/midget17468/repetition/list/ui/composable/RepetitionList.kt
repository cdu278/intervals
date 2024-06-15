package midget17468.repetition.list.ui.composable

import androidx.compose.foundation.layout.Arrangement
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
import midget17468.loadable.ui.Loadable.Loaded
import midget17468.loadable.ui.Loadable.Loading
import midget17468.ui.composable.doubleMargin
import midget17468.ui.composable.halfMargin
import midget17468.memo.memo_list.R
import midget17468.repetition.item.ui.UiRepetitionItem
import midget17468.repetition.item.ui.composable.RepetitionItem
import midget17468.repetition.list.ui.component.RepetitionListComponent

@Composable
fun RepetitionList(
    component: RepetitionListComponent,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        val model by component.uiModelFlow.collectAsState()
        when (val m = model) {
            is Loading -> { }
            is Loaded -> {
                val items = m.value
                if (items.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(halfMargin),
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(items, key = UiRepetitionItem::id) {
                            RepetitionItem(
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