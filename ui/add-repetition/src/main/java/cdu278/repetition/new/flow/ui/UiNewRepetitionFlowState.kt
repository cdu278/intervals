package cdu278.repetition.new.flow.ui

import cdu278.repetition.new.editor.ui.component.NewRepetitionEditorComponent
import cdu278.repetition.new.flow.ui.component.NewRepetitionButtonComponent

sealed interface UiNewRepetitionFlowState {

    data object Initial : UiNewRepetitionFlowState

    data class AddButton(
        val component: NewRepetitionButtonComponent,
    ) : UiNewRepetitionFlowState

    data class Editor(
        val component: NewRepetitionEditorComponent<*>,
    ) : UiNewRepetitionFlowState
}