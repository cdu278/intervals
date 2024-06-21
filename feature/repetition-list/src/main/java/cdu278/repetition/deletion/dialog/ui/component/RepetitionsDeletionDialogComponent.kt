package cdu278.repetition.deletion.dialog.ui.component

import cdu278.decompose.context.coroutineScope
import cdu278.repetition.deletion.dialog.ui.DeletedRepetitionsCount
import cdu278.repetition.s.repository.RepetitionsRepository
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class RepetitionsDeletionDialogComponent(
    context: ComponentContext,
    private val idsOfRepetitions: List<Long>,
    private val repository: RepetitionsRepository,
    private val dismiss: () -> Unit,
    private val onDeleted: () -> Unit,
) : ComponentContext by context {

    internal val deletedCount: DeletedRepetitionsCount =
        if (idsOfRepetitions.size > 1) {
            DeletedRepetitionsCount.Multiple(idsOfRepetitions.size)
        } else {
            DeletedRepetitionsCount.Single
        }

    internal fun delete() {
        coroutineScope().launch {
            idsOfRepetitions
                .map { id ->
                    launch {
                        repository
                            .repetitionRepository(id)
                            .delete()
                    }
                }
                .joinAll()
            onDeleted.invoke()
            dismiss()
        }
    }

    internal fun dismiss() {
        this.dismiss.invoke()
    }
}