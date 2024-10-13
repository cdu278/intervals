package cdu278.repetition.list.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import cdu278.intervals.repetition.list.R
import cdu278.repetition.item.ui.composable.DefaultRepetitionItem
import cdu278.repetition.item.ui.composable.SelectionRepetitionItem
import cdu278.repetition.list.ui.RepetitionListUi.State.Empty
import cdu278.repetition.list.ui.RepetitionListUi.State.NonEmpty
import cdu278.repetition.list.ui.RepetitionListUi.State.NonEmpty.Mode.Default
import cdu278.repetition.list.ui.RepetitionListUi.State.NonEmpty.Mode.Selection
import cdu278.repetition.list.ui.component.RepetitionListComponent
import cdu278.ui.composable.defaultMargin
import cdu278.ui.composable.doubleMargin
import cdu278.ui.composable.halfMargin

@Composable
fun RepetitionList(
    component: RepetitionListComponent,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        val model by component.uiModelFlow.collectAsState()
        when (val state = model.state ?: return@Box) {
            is Empty ->
                Text(
                    text = stringResource(R.string.noItemsYet),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = doubleMargin),
                )
            is NonEmpty -> {
                val accentColor = MaterialTheme.colorScheme.tertiary
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(halfMargin),
                    modifier = Modifier
                        .padding(
                            start = defaultMargin,
                            top = defaultMargin,
                            end = defaultMargin,
                        )
                ) {
                    when (val mode = state.mode) {
                        is Default ->
                            items(mode.items, key = { it.info.id }) { item ->
                                DefaultRepetitionItem(
                                    item,
                                    accentColor = accentColor,
                                    modifier = Modifier
                                )
                            }
                        is Selection ->
                            items(mode.items, key = { it.info.id }) { item ->
                                SelectionRepetitionItem(
                                    item,
                                    accentColor = accentColor,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                    }
                }
            }
        }
    }
}