package midget17468.memo.decompose.navigation.config

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize
import midget17468.memo.model.domain.MemoType

sealed interface NewMemoFlowStateConfig : Parcelable {

    @Parcelize
    data object AddButton : NewMemoFlowStateConfig

    @Parcelize
    data class Editor(
        val type: MemoType
    ) : NewMemoFlowStateConfig
}