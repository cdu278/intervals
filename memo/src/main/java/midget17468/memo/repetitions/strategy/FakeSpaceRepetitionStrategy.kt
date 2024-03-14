package midget17468.memo.repetitions.strategy

import midget17468.memo.model.domain.RepetitionStage
import kotlin.time.Duration

class FakeSpaceRepetitionStrategy(
    private val duration: Duration
) : SpaceRepetitionStrategy {

    override fun RepetitionStage.space(): Duration = duration
}