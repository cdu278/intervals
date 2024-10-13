package cdu278.repetition.root.main.ui

import cdu278.repetition.list.ui.component.RepetitionListComponent
import cdu278.repetition.root.main.tab.MainTab
import cdu278.ui.action.UiAction

internal data class UiMainTab(
    val tab: MainTab,
    val active: Boolean = false,
    val activate: UiAction = UiAction(key = null) { },
    val listComponent: RepetitionListComponent? = null,
)