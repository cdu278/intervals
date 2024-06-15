package midget17468.ui.input.validated

sealed interface Validated<out T> {

    class Invalid(val error: String = "") : Validated<Nothing>

    class Valid<T>(val value: T) : Validated<T>
}