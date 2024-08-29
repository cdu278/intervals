package cdu278.repetition.intervals.strategy

import cdu278.repetition.stage.RepetitionStage
import kotlin.time.Duration

class FakeRepetitionIntervalsStrategy(
    private val duration: Duration
) : RepetitionIntervalsStrategy {

    override fun RepetitionStage.space(): Duration = duration
}