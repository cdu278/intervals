package cdu278.repetition.list.ui

import kotlinx.serialization.Serializable

@Serializable
data class RepetitionListInput(
    val idOfExpanded: Long? = null,
)