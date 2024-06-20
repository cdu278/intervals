package cdu278.repetition.root.main.ui.component

import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.intervals.ui.component.context.childContext
import cdu278.repetition.list.ui.component.RepetitionListComponent
import cdu278.repetition.new.flow.ui.component.NewRepetitionFlowComponent
import cdu278.repetition.s.repository.RepetitionsRepository

class MainComponent(
    context: IntervalsComponentContext,
    repetitionsRepository: RepetitionsRepository,
    repeat: (repetitionId: Long) -> Unit,
) : IntervalsComponentContext by context {

    val repetitionListComponent =
        RepetitionListComponent(
            childContext("passwordList"),
            repetitionsRepository,
            repeat,
        )

    val newRepetitionFlowComponent =
        NewRepetitionFlowComponent(
            childContext("newRepetitionFlow"),
            repetitionNotifications,
            repetitionsRepository,
            hashes,
            spacedRepetitions,
        )
}