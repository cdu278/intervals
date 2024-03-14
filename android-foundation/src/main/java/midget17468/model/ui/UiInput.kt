package midget17468.model.ui

data class UiInput<T>(
    val value: T,
    val change: ChangeInput<T>
)