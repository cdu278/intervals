package midget17468.ui.input

import midget17468.ui.input.change.ChangeInput

data class UiInput<T>(
    val value: T,
    val change: ChangeInput<T>
)