package midget17468.repetition.spaced.strategy

import midget17468.repetition.stage.RepetitionStage
import kotlin.time.Duration

class FakeSpaceRepetitionsStrategy(
    private val duration: Duration
) : SpaceRepetitionsStrategy {

    override fun RepetitionStage.space(): Duration = duration
}