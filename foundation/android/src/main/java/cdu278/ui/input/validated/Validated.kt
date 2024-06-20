package cdu278.ui.input.validated

sealed interface Validated<out T, out Error> {

    class Invalid<Error>(val error: Error) : Validated<Nothing, Error>

    class Valid<T>(val value: T) : Validated<T, Nothing>
}