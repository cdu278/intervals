package midget17468.memo.model.ui

import midget17468.memo.model.domain.MemoType
import midget17468.model.ui.UiInput

internal data class UiRepetition(
    val type: MemoType,
    val label: String,
    val state: State,
) {

    sealed interface State {

        data class RepetitionAt(
            val date: NextRepetitionDate,
        ) : State

        data class Checking(
            val mode: Mode,
            val data: UiInput<String>,
            val error: String?,
            val hintState: HintState?,
        ) : State {

            enum class Mode { Repetition, Remembering }

            sealed interface HintState {

                data object Hidden : HintState

                data class Shown(val text: String) : HintState
            }
        }

        data object Forgotten : State
    }
}