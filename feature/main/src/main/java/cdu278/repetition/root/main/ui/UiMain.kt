package cdu278.repetition.root.main.ui

import cdu278.ui.action.UiAction

sealed interface UiMain {

    data object Default : UiMain

    data class Selection(
        val selectedCount: Int,
        val delete: UiAction,
        val quitSelectionModel: UiAction,
    ) : UiMain
}