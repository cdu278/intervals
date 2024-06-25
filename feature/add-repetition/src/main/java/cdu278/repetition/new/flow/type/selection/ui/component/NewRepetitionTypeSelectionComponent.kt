package cdu278.repetition.new.flow.type.selection.ui.component

import cdu278.repetition.RepetitionType
import cdu278.repetition.new.flow.type.selection.ui.UiRepetitionTypeSelection
import cdu278.ui.action.UiAction
import com.arkivanov.decompose.ComponentContext

class NewRepetitionTypeSelectionComponent(
    context: ComponentContext,
    private val onSelected: (RepetitionType) -> Unit,
    private val close: () -> Unit,
) : ComponentContext by context {

    val model =
        UiRepetitionTypeSelection(
            items = RepetitionType.entries.map { type ->
                UiRepetitionTypeSelection.Item(
                    type,
                    choose = UiAction(key = type) { onSelected(type) },
                )
            }
        )

    fun close() {
        this.close.invoke()
    }
}