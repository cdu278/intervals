package midget17468.repetition.new.data.ui.component

import kotlinx.coroutines.flow.StateFlow
import midget17468.ui.input.validated.Validated

interface NewRepetitionDataComponent {

    val dataFlow: StateFlow<Validated<String>>
}