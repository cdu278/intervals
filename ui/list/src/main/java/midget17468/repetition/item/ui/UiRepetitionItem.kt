package midget17468.repetition.item.ui

import androidx.compose.runtime.Stable
import midget17468.repetition.RepetitionType
import midget17468.repetition.next.NextRepetitionDate
import midget17468.ui.action.UiAction

internal data class UiRepetitionItem(
    val id: Int,
    val info: Info,
    val state: State,
    val repeat: UiAction,
    val expanded: Expanded,
    val delete: UiAction,
) {

    @Stable
    val isExpanded: Boolean
        get() = expanded.value

    data class Info(
        val label: String,
        val type: RepetitionType,
    )

    sealed interface State {

        data object Repetition : State

        data class RepetitionAt(
            val date: NextRepetitionDate,
        ) : State

        data object Forgotten : State
    }

    data class Expanded(
        val value: Boolean,
        val toggle: UiAction,
    )
}