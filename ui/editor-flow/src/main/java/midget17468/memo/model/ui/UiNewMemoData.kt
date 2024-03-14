package midget17468.memo.model.ui

import midget17468.memo.decompose.component.NewMemoDataComponent
import midget17468.memo.decompose.component.NewPasswordDataComponent

sealed interface UiNewMemoData {

    val component: NewMemoDataComponent

    data class Password(
        override val component: NewPasswordDataComponent<*>
    ) : UiNewMemoData
}