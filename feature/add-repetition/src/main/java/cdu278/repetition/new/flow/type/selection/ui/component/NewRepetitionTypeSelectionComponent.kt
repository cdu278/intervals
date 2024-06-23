package cdu278.repetition.new.flow.type.selection.ui.component

import cdu278.repetition.RepetitionType
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.RepetitionType.Pin
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
            items = listOf(
                UiRepetitionTypeSelection.Item(
                    type = Password,
                    choose = UiAction(key = null) { onSelected(Password) }
                ),
                UiRepetitionTypeSelection.Item(
                    type = Pin,
                    choose = UiAction(key = null) { onSelected(Pin) }
                ),
            )
        )

    fun close() {
        this.close.invoke()
    }
}