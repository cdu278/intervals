package cdu278.repetition.ui

import cdu278.repetition.ui.component.ArchiveConfirmationDialogComponent
import cdu278.ui.action.UiAction

internal sealed interface UiRepetitionDialog {

    val dismiss: UiAction

    data class ArchiveConfirmation(
        val component: ArchiveConfirmationDialogComponent,
        val confirm: UiAction,
        override val dismiss: UiAction,
    ) : UiRepetitionDialog
}