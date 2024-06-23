package cdu278.repetition.root.ui

import cdu278.repetition.root.main.ui.component.MainComponent
import cdu278.repetition.ui.component.RepetitionComponent

internal sealed interface UiRootScreen {

    data class Main(
        val component: MainComponent,
    ) : UiRootScreen

    data class Repetition(
        val component: RepetitionComponent,
    ) : UiRootScreen
}