package cdu278.repetition.root.ui

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface ScreenConfig : Parcelable {

    @Parcelize
    data object Main : ScreenConfig

    @Parcelize
    data class Repetition(
        val id: Long
    ) : ScreenConfig
}