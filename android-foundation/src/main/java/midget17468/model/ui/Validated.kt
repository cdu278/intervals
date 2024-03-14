package midget17468.model.ui

sealed interface Validated<out T> {

    class Invalid(val error: String = "") : Validated<Nothing>

    class Valid<T>(val value: T) : Validated<T>
}