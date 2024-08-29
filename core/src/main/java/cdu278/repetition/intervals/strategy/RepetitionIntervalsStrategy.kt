package cdu278.repetition.intervals.strategy

import cdu278.repetition.stage.RepetitionStage
import kotlin.time.Duration

interface RepetitionIntervalsStrategy {

    fun RepetitionStage.space(): Duration
}