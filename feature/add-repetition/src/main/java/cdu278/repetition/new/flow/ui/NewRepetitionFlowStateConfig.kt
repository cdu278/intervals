package cdu278.repetition.new.flow.ui

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize
import cdu278.repetition.RepetitionType

sealed interface NewRepetitionFlowStateConfig : Parcelable {

    @Parcelize
    data object AddButton : NewRepetitionFlowStateConfig

    @Parcelize
    data class Editor(
        val type: RepetitionType
    ) : NewRepetitionFlowStateConfig
}