package cdu278.repetition.ui

import cdu278.repetition.RepetitionType
import cdu278.repetition.next.NextRepetitionDate
import cdu278.ui.input.UiInput

internal data class UiRepetition(
    val type: RepetitionType,
    val label: String,
    val state: State,
    val dialog: UiRepetitionDialog?,
) {

    sealed interface State {

        data class RepetitionAt(
            val date: NextRepetitionDate,
        ) : State

        data class Checking(
            val mode: Mode,
            val data: UiInput<String>,
            val message: Message?,
            val valid: Boolean,
            val hintState: HintState?,
            val inProgress: Boolean,
        ) : State {

            enum class Mode { Repetition, Remembering }

            sealed interface HintState {

                data object Hidden : HintState

                data class Shown(val text: String) : HintState
            }

            sealed interface Message {

                data class DataEmpty(
                    val type: RepetitionType
                ) : Message

                data object Failed : Message
            }
        }

        data object Forgotten : State
    }
}