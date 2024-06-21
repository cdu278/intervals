package cdu278.repetition.root.main.ui

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal sealed interface MainDialogConfig : Parcelable {

    @Parcelize
    data class Deletion(val idsOfRepetitions: List<Long>) : MainDialogConfig
}