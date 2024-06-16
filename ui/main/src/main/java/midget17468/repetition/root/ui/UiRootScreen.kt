package midget17468.repetition.root.ui

import midget17468.repetition.new.error.owner.EmptyPasswordErrorOwner
import midget17468.repetition.root.main.ui.component.MainComponent
import midget17468.repetition.ui.component.RepetitionComponent

sealed interface UiRootScreen {

    data class Main(
        val component: MainComponent,
    ) : UiRootScreen

    data class Repetition(
        val component: RepetitionComponent<EmptyPasswordErrorOwner>,
    ) : UiRootScreen
}