package midget17468.memo.model.ui

import midget17468.memo.model.domain.MemoType
import midget17468.model.ui.UiAction

internal data class UiMemoItem(
    val id: Int,
    val info: Info,
    val state: State,
    val repeat: UiAction,
) {

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
}