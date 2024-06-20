package cdu278.repetition.new.data.ui

import cdu278.repetition.new.data.ui.component.NewPasswordDataComponent
import cdu278.repetition.new.data.ui.component.NewRepetitionDataComponent

sealed interface UiNewRepetitionData<Error> {

    val component: NewRepetitionDataComponent<Error>

    data class Password<Error>(
        override val component: NewPasswordDataComponent<Error>
    ) : UiNewRepetitionData<Error>
}