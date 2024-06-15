package midget17468.repetition.main.ui.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.instancekeeper.getOrCreateSimple
import com.arkivanov.essenty.lifecycle.doOnResume
import midget17468.hash.algorithm.HashAlgorithm
import midget17468.repetition.spaced.SpacedRepetitions
import midget17468.repetition.RepetitionDb
import midget17468.repetition.list.ui.component.RepetitionListComponent
import midget17468.repetition.new.error.NewRepetitionValidationErrors
import midget17468.repetition.new.flow.ui.component.NewRepetitionFlowComponent
import midget17468.repetition.notification.s.RepetitionsNotifications
import midget17468.repetition.s.repository.RepetitionsRepositoryInstance
import midget17468.updates.Updates

class MainComponent(
    context: ComponentContext,
    db: RepetitionDb,
    errors: NewRepetitionValidationErrors,
    repetitionNotifications: RepetitionsNotifications,
    hashAlgorithm: HashAlgorithm,
    repeat: (repetitionId: Int) -> Unit,
    spacedRepetitions: SpacedRepetitions,
) : ComponentContext by context {

    private val updates =
        instanceKeeper.getOrCreateSimple("repositoryUpdates") { Updates() }

    private val repository =
        instanceKeeper.getOrCreate {
            RepetitionsRepositoryInstance(db.repetitionQueries, updates)
        }

    init {
        lifecycle.doOnResume { updates.post() }
    }

    val repetitionListComponent =
        RepetitionListComponent(
            childContext("passwordList"),
            repository,
            repetitionNotifications,
            repeat,
        )

    val newRepetitionFlowComponent =
        NewRepetitionFlowComponent(
            childContext("newRepetitionFlow"),
            errors,
            repetitionNotifications,
            repository,
            hashAlgorithm,
            spacedRepetitions,
        )
}