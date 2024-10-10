package cdu278.repetition.list.ui

import cdu278.repetition.RepetitionType
import kotlinx.serialization.Serializable

@Serializable
data class RepetitionListState(
    val selectedType: RepetitionType? = null,
    val mode: Mode = Mode.Default,
) {

    sealed interface Mode {

        @Serializable
        data object Default : Mode

        @Serializable
        data class Selection(
            val idsOfSelected: List<Long>,
        ) : Mode
    }
}