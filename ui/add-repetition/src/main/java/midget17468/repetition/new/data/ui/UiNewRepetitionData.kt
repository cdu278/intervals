package midget17468.repetition.new.data.ui

import midget17468.repetition.new.data.ui.component.NewPasswordDataComponent
import midget17468.repetition.new.data.ui.component.NewRepetitionDataComponent

sealed interface UiNewRepetitionData {

    val component: NewRepetitionDataComponent

    data class Password(
        override val component: NewPasswordDataComponent<*>
    ) : UiNewRepetitionData
}