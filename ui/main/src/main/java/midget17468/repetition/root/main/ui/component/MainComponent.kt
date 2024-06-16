package midget17468.repetition.root.main.ui.component

import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.instancekeeper.getOrCreateSimple
import com.arkivanov.essenty.lifecycle.doOnResume
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import midget17468.repetition.list.ui.component.RepetitionListComponent
import midget17468.repetition.new.error.NewRepetitionValidationErrors
import midget17468.repetition.new.flow.ui.component.NewRepetitionFlowComponent
import midget17468.repetition.s.repository.RepetitionsRepositoryInstance
import midget17468.updates.Updates

class MainComponent(
    context: IntervalsComponentContext,
    errors: NewRepetitionValidationErrors,
    repeat: (repetitionId: Int) -> Unit,
) : IntervalsComponentContext by context {

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
            hashes,
            spacedRepetitions,
        )
}