package midget17468.model.ui

sealed interface Loadable<out T> {

    data object Loading : Loadable<Nothing>

    data class Loaded<T>(val value: T) : Loadable<T>
}