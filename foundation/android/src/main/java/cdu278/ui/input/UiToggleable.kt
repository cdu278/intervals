package cdu278.ui.input

import cdu278.ui.action.UiAction

data class UiToggleable(
    val value: Boolean,
    val toggle: UiAction,
)