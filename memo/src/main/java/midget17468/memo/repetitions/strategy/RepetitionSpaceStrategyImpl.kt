package midget17468.memo.repetitions.strategy

import midget17468.memo.model.domain.RepetitionStage
import kotlin.time.Duration

class DefaultSpaceRepetitionStrategy: SpaceRepetitionStrategy {

    override fun RepetitionStage.space(): Duration = this.space
}