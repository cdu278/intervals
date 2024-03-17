package midget17468.memo.repetitions.strategy

import midget17468.memo.model.domain.RepetitionStage
import kotlin.time.Duration

class FakeSpaceRepetitionsStrategy(
    private val duration: Duration
) : SpaceRepetitionsStrategy {

    override fun RepetitionStage.space(): Duration = duration
}