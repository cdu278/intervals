package cdu278.repetition.root.main.ui

import cdu278.ui.action.UiAction

internal sealed interface UiMainMode {

    data object Default : UiMainMode

    data class Selection(
        val selectedCount: Int,
        val delete: UiAction,
        val quitSelectionModel: UiAction,
        val dialog: UiMainDialog?,
    ) : UiMainMode
}