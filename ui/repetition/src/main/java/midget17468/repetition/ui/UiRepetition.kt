package midget17468.repetition.ui

import midget17468.repetition.RepetitionType
import midget17468.repetition.next.NextRepetitionDate
import midget17468.ui.input.UiInput

internal data class UiRepetition(
    val type: RepetitionType,
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
            val inProgress: Boolean,
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