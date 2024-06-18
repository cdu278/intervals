package cdu278.ui.input

import cdu278.ui.input.change.ChangeInput

data class UiInput<T>(
    val value: T,
    val change: ChangeInput<T>
)