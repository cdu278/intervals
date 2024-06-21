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
import cdu278.ui.composable.doubleMargin
import cdu278.ui.composable.halfMargin
import cdu278.intervals.repetition.list.ui.R
import cdu278.repetition.item.ui.composable.DefaultRepetitionItem
import cdu278.repetition.item.ui.composable.SelectionRepetitionItem
import cdu278.repetition.list.ui.UiRepetitionList.Empty
import cdu278.repetition.list.ui.UiRepetitionList.NonEmpty
import cdu278.repetition.list.ui.UiRepetitionList.NonEmpty.Mode.Default
import cdu278.repetition.list.ui.UiRepetitionList.NonEmpty.Mode.Selection
import cdu278.repetition.list.ui.component.RepetitionListComponent

@Composable
fun RepetitionList(
    component: RepetitionListComponent,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        val model by component.uiModelFlow.collectAsState()
        when (val m = model ?: return@Box) {
            is Empty ->
                Text(
                    text = stringResource(R.string.noItemsYet),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = doubleMargin),
                )
            is NonEmpty -> {
                val primaryStyle =
                    MaterialTheme.typography
                        .bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                val secondaryStyle =
                    MaterialTheme.typography
                        .bodySmall.copy(color = MaterialTheme.colorScheme.secondary)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(halfMargin),
                ) {
                    when (val mode = m.mode) {
                        is Default ->
                            items(mode.items, key = { it.info.id }) { item ->
                                DefaultRepetitionItem(
                                    item,
                                    primaryStyle,
                                    secondaryStyle,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }

                        is Selection ->
                            items(mode.items, key = { it.info.id }) { item ->
                                SelectionRepetitionItem(
                                    item,
                                    primaryStyle,
                                    secondaryStyle,
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