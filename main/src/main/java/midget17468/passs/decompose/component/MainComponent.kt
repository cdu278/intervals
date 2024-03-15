package midget17468.passs.decompose.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.instancekeeper.getOrCreateSimple
import com.arkivanov.essenty.lifecycle.doOnResume
import midget17468.hash.algorithm.HashAlgorithm
import midget17468.memo.MemoDb
import midget17468.memo.decompose.component.MemoListComponent
import midget17468.memo.decompose.component.NewMemoFlowComponent
import midget17468.memo.decompose.instance.MemoRepositoryInstance
import midget17468.memo.model.domain.NewMemoValidationErrors
import midget17468.memo.repetitions.SpacedRepetitions
import midget17468.memo.repetitions.notifications.RepetitionsNotifications
import midget17468.memo.repetitions.strategy.FakeSpaceRepetitionStrategy
import midget17468.memo.repetitions.strategy.SpaceRepetitionStrategy
import midget17468.repository.updates.RepositoryUpdates
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MainComponent(
    context: ComponentContext,
    memoDb: MemoDb,
    errors: NewMemoValidationErrors,
    repetitionNotifications: RepetitionsNotifications,
    hashAlgorithm: HashAlgorithm,
    repeatMemo: (id: Int) -> Unit,
    spaceRepetitionStrategy: SpaceRepetitionStrategy,
) : ComponentContext by context {

    private val repositoryUpdates =
        instanceKeeper.getOrCreateSimple("repositoryUpdates") { RepositoryUpdates() }

    private val repository =
        instanceKeeper.getOrCreate {
            MemoRepositoryInstance(memoDb.memoQueries, repositoryUpdates)
        }

    init {
        lifecycle.doOnResume { repositoryUpdates.post() }
    }

    val memoListComponent =
        MemoListComponent(
            childContext("passwordList"),
            repository,
            repetitionNotifications,
            repeatMemo,
        )

    val newMemoFlowComponent =
        NewMemoFlowComponent(
            childContext("newMemoFlow"),
            errors,
            repetitionNotifications,
            repository,
            hashAlgorithm,
            spacedRepetitions = SpacedRepetitions(spaceRepetitionStrategy),
        )
}