package cdu278.repetition.new.data.ui

import cdu278.repetition.new.data.ui.component.NewPasswordDataComponent
import cdu278.repetition.new.data.ui.component.NewRepetitionDataComponent

sealed interface UiNewRepetitionData {

    val component: NewRepetitionDataComponent

    data class Password(
        override val component: NewPasswordDataComponent<*>
    ) : UiNewRepetitionData
}