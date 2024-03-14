package midget17468.memo.model.ui

import midget17468.memo.decompose.component.NewMemoButtonComponent
import midget17468.memo.decompose.component.NewMemoEditorComponent

sealed interface UiNewMemoFlowState {

    data object Initial : UiNewMemoFlowState

    data class AddButton(
        val component: NewMemoButtonComponent,
    ) : UiNewMemoFlowState

    data class Editor(
        val component: NewMemoEditorComponent<*>,
    ) : UiNewMemoFlowState
}