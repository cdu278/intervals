package midget17468.repetition.spaced.strategy

import midget17468.repetition.stage.RepetitionStage
import kotlin.time.Duration

interface SpaceRepetitionsStrategy {

    fun RepetitionStage.space(): Duration
}