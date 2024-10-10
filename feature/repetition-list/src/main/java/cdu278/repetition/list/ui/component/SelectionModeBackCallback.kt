package cdu278.repetition.list.ui.component

import cdu278.repetition.list.ui.RepetitionListState
import cdu278.repetition.list.ui.RepetitionListState.Mode.Default
import cdu278.state.State
import com.arkivanov.essenty.backhandler.BackCallback

internal class SelectionModeBackCallback(
    private val state: State<RepetitionListState>,
) : BackCallback() {

    override fun onBack() {
        state.update { it.copy(mode = Default) }
    }
}