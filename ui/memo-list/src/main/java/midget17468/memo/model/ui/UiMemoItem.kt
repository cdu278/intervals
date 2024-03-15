package midget17468.memo.model.ui

import androidx.compose.runtime.Stable
import midget17468.memo.model.domain.MemoType
import midget17468.model.ui.UiAction

internal data class UiMemoItem(
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
        val type: MemoType,
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