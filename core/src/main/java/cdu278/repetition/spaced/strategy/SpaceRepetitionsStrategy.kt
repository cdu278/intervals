package cdu278.repetition.spaced.strategy

import cdu278.repetition.stage.RepetitionStage
import kotlin.time.Duration

interface SpaceRepetitionsStrategy {

    fun RepetitionStage.space(): Duration
}