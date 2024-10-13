package cdu278.repetition.root.main.ui

import cdu278.repetition.list.tabs.ui.component.RepetitionListTabsComponent
import cdu278.repetition.root.main.tab.MainTab

internal data class UiMain(
    val mode: UiMainMode = UiMainMode.Default,
    val navigationTabs: List<UiMainTab> = List(size = 3) { UiMainTab(MainTab.entries[it]) },
    val listTabsComponent: RepetitionListTabsComponent? = null,
)