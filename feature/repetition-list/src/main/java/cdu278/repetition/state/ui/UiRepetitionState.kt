package cdu278.repetition.state.ui

import cdu278.repetition.next.NextRepetitionDate
import cdu278.ui.action.UiAction

sealed interface UiRepetitionState {

    data class Repetition(
        val repeat: UiAction,
    ) : UiRepetitionState

    data class RepetitionAt(
        val date: NextRepetitionDate,
    ) : UiRepetitionState

    data class Forgotten(
        val remember: UiAction,
    ) : UiRepetitionState
}