package cdu278.repetition.list.ui

import cdu278.repetition.info.ui.UiRepetitionInfo
import cdu278.repetition.state.ui.UiRepetitionState
import cdu278.ui.action.UiAction

internal sealed interface UiRepetitionList {

    data object Empty : UiRepetitionList

    data class NonEmpty(
        val mode: Mode,
    ) : UiRepetitionList {

        sealed interface Mode {

            data class Default(
                val items: List<Item> = emptyList(),
            ) : Mode {

                data class Item(
                    val info: UiRepetitionInfo,
                    val state: UiRepetitionState,
                    val select: UiAction,
                )
            }

            data class Selection(
                val items: List<Item>,
            ) : Mode {

                data class Item(
                    val info: UiRepetitionInfo,
                    val selected: Boolean,
                    val toggleSelected: UiAction,
                )
            }
        }
    }
}