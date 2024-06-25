package cdu278.repetition

import androidx.annotation.Keep
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import cdu278.repetition.stage.RepetitionStage

@Serializable
@Keep
sealed interface RepetitionState {

    val hintShown: Boolean

    fun withHintShown(value: Boolean): RepetitionState

    @Serializable
    @Keep
    data class Repetition(
        @Keep
        val date: LocalDateTime,
        @Keep
        val stage: RepetitionStage,
        @Keep
        override val hintShown: Boolean,
    ) : RepetitionState {

        override fun withHintShown(value: Boolean): RepetitionState {
            return copy(hintShown = value)
        }
    }

    @Serializable
    @Keep
    data class Forgotten(
        @Keep
        override val hintShown: Boolean = false,
    ) : RepetitionState {

        override fun withHintShown(value: Boolean): RepetitionState {
            return copy(hintShown = value)
        }
    }
}