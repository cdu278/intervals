package cdu278.repetition.root.main.ui

internal data class UiMain(
    val mode: UiMainMode = UiMainMode.Default,
    val tabs: List<UiMainTab> = List(size = 3) { UiMainTab(MainTabConfig.entries[it]) },
)