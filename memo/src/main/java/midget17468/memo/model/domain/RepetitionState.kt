package midget17468.memo.model.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
sealed interface RepetitionState {

    @Serializable
    data class Repetition(
        val date: LocalDateTime,
        val stage: RepetitionStage,
    ) : RepetitionState

    @Serializable
    data object Forgotten : RepetitionState
}