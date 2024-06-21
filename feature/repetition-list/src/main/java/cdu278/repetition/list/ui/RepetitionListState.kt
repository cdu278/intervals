package cdu278.repetition.list.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface RepetitionListState {

    @Serializable
    data object Default : RepetitionListState

    @Serializable
    data class Selection(
        val idsOfSelected: List<Long>,
    ) : RepetitionListState
}