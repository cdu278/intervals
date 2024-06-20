package cdu278.repetition.new.data.ui.component

import kotlinx.coroutines.flow.StateFlow
import cdu278.ui.input.validated.Validated

interface NewRepetitionDataComponent<Error> {

    val dataFlow: StateFlow<Validated<String, Error>>
}