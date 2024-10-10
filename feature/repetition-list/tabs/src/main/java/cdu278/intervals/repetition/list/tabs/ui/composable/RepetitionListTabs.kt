package cdu278.intervals.repetition.list.tabs.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cdu278.intervals.repetition.list.tabs.R
import cdu278.intervals.repetition.list.tabs.ui.component.RepetitionListTabsComponent
import cdu278.repetition.RepetitionType
import cdu278.ui.composable.halfMargin

@Composable
fun RepetitionListTabs(
    component: RepetitionListTabsComponent,
    modifier: Modifier = Modifier,
) {
    val tabsState = component.tabsFlow.collectAsState()
    val tabs = tabsState.value ?: return
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(halfMargin),
        modifier = modifier
    ) {
        items(tabs, key = { it.type ?: "null" }) { tab ->
            FilterChip(
                selected = tab.selected.value,
                onClick = tab.selected.toggle,
                label = { Text(tab.type.text) },
            )
        }
        item("spacer") {
            Spacer(Modifier.width(halfMargin))
        }
    }
}

private val RepetitionType?.text: String
    @Composable
    get() = stringResource(
        id = when(this) {
            null -> R.string.repetition_tab_all
            RepetitionType.Password -> R.string.repetition_tab_passwords
            RepetitionType.Pin -> R.string.repetition_tab_pins
            RepetitionType.Email -> R.string.repetition_tab_emails
            RepetitionType.Phone -> R.string.repetition_tab_phones
        }
    )