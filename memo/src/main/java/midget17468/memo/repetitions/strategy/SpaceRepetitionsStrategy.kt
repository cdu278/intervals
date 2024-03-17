package midget17468.memo.repetitions.strategy

import midget17468.memo.model.domain.RepetitionStage
import kotlin.time.Duration

interface SpaceRepetitionsStrategy {

    fun RepetitionStage.space(): Duration
}