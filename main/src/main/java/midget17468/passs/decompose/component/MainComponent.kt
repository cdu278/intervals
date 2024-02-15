package midget17468.passs.decompose.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDateTime
import midget17468.passs.computable.parametrized.ParametrizedComputable
import midget17468.passs.decompose.context.coroutineScope
import midget17468.passs.decompose.retained.flow.retainedFlow
import midget17468.passs.directions.MainDirections
import midget17468.passs.flow.sharingStarted.uiModelSharingStarted
import midget17468.passs.model.ui.UiPasswordList
import midget17468.passs.model.ui.UiPasswordItem
import midget17468.passs.model.ui.ViewAction
import midget17468.passs.repository.MainRepository

class MainComponent(
    context: ComponentContext,
    repository: MainRepository,
    private val directions: MainDirections,
    private val nextCheckDate: ParametrizedComputable<LocalDateTime, UiPasswordItem.NextCheckDate>,
) : ComponentContext by context {

    private val retainedItemsFlow = retainedFlow(key = "items", repository.readItems())

    val uiModelFlow: StateFlow<UiPasswordList> =
        retainedItemsFlow
            .map { items ->
                UiPasswordList.Loaded(items = items.sortedBy { it.nextCheckDate }.map { item ->
                    UiPasswordItem(
                        item.id,
                        item.type,
                        nextCheckDate(item.nextCheckDate),
                        open = ViewAction { directions.toItem(item.id) },
                    )
                })
            }
            .stateIn(coroutineScope(), uiModelSharingStarted, initialValue = UiPasswordList.Loading)
}