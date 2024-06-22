package cdu278.repetition.dialog.ui

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface RepetitionDialogConfig : Parcelable {

    @Parcelize
    data object ArchiveConfirmation : RepetitionDialogConfig
}