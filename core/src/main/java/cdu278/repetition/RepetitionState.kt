package cdu278.repetition

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import cdu278.repetition.stage.RepetitionStage

@Serializable
sealed interface RepetitionState {

    val hintShown: Boolean

    fun withHintShown(value: Boolean): RepetitionState

    @Serializable
    data class Repetition(
        val date: LocalDateTime,
        val stage: RepetitionStage,
        override val hintShown: Boolean,
    ) : RepetitionState {

        override fun withHintShown(value: Boolean): RepetitionState {
            return copy(hintShown = value)
        }
    }

    @Serializable
    data class Forgotten(
        override val hintShown: Boolean = false,
    ) : RepetitionState {

        override fun withHintShown(value: Boolean): RepetitionState {
            return copy(hintShown = value)
        }
    }
}