package cdu278.repetition.new.data.ui

import cdu278.repetition.new.data.ui.component.NewEmailDataComponent
import cdu278.repetition.new.data.ui.component.NewPasswordDataComponent
import cdu278.repetition.new.data.ui.component.NewPhoneDataComponent
import cdu278.repetition.new.data.ui.component.NewRepetitionDataComponent

sealed interface UiNewRepetitionData<Error> {

    val component: NewRepetitionDataComponent<Error>

    data class Password<Error>(
        override val component: NewPasswordDataComponent<Error>
    ) : UiNewRepetitionData<Error>

    data class Pin<Error>(
        override val component: NewPasswordDataComponent<Error>,
    ) : UiNewRepetitionData<Error>

    data class Email<Error>(
        override val component: NewEmailDataComponent<Error>,
    ) : UiNewRepetitionData<Error>

    data class Phone<Error>(
        override val component: NewPhoneDataComponent<Error>
    ) : UiNewRepetitionData<Error>
}