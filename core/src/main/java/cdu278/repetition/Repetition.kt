package cdu278.repetition

interface Repetition {

    val id: Long

    val label: String

    val type: RepetitionType

    val data: RepetitionData

    val state: RepetitionState

    val hint: String?
}