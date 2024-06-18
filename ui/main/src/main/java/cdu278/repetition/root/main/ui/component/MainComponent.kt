package cdu278.repetition.root.main.ui.component

import cdu278.repetition.s.repository.RoomRepetitionsRepository
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.intervals.ui.component.context.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import cdu278.repetition.list.ui.component.RepetitionListComponent
import cdu278.repetition.new.error.owner.NewRepetitionValidationErrors
import cdu278.repetition.new.flow.ui.component.NewRepetitionFlowComponent
import cdu278.repetition.s.repository.RepetitionsRepositoryInstance

class MainComponent(
    context: IntervalsComponentContext,
    errors: NewRepetitionValidationErrors,
    repeat: (repetitionId: Long) -> Unit,
) : IntervalsComponentContext by context {

    private val repository =
        instanceKeeper.getOrCreate {
            RepetitionsRepositoryInstance(
                repository = {
                    RoomRepetitionsRepository(
                        db.repetitionsDao,
                        db.repetitionDao,
                    )
                }
            )
        }

    val repetitionListComponent =
        RepetitionListComponent(
            childContext("passwordList"),
            repository,
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