package cdu278.repetition.spaced.strategy

import cdu278.repetition.stage.RepetitionStage
import kotlin.time.Duration

class FakeSpaceRepetitionsStrategy(
    private val duration: Duration
) : SpaceRepetitionsStrategy {

    override fun RepetitionStage.space(): Duration = duration
}