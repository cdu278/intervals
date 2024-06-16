package midget17468.repetition.root.main.ui.component

import cdu278.intervals.repetition.s.repository.RoomRepetitionsRepository
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import midget17468.repetition.list.ui.component.RepetitionListComponent
import midget17468.repetition.new.error.NewRepetitionValidationErrors
import midget17468.repetition.new.flow.ui.component.NewRepetitionFlowComponent
import midget17468.repetition.s.repository.RepetitionsRepositoryInstance

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