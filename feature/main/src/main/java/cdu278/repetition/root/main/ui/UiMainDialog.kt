package cdu278.repetition.root.main.ui

import cdu278.repetition.deletion.dialog.ui.component.RepetitionsDeletionDialogComponent

sealed interface UiMainDialog {

    data class Deletion(
        val component: RepetitionsDeletionDialogComponent,
    ) : UiMainDialog
}