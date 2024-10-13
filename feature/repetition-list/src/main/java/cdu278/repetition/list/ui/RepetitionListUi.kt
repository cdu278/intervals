package cdu278.repetition.list.ui

import cdu278.repetition.info.ui.UiRepetitionInfo
import cdu278.repetition.state.ui.UiRepetitionState
import cdu278.ui.action.UiAction

internal data class RepetitionListUi(
    val state: State?,
) {

    sealed interface State {

        data object Empty : State

        data class NonEmpty(
            val mode: Mode,
        ) : State {

            sealed interface Mode {

                data class Default(
                    val items: List<Item> = emptyList(),
                ) : Mode {

                    data class Item(
                        val info: UiRepetitionInfo,
                        val state: UiRepetitionState,
                        val select: UiAction,
                        val progressRatio: Double?,
                    )
                }

                data class Selection(
                    val items: List<Item>,
                ) : Mode {

                    data class Item(
                        val info: UiRepetitionInfo,
                        val selected: Boolean,
                        val toggleSelected: UiAction,
                        val progressRatio: Double?,
                    )
                }
            }
        }
    }
}