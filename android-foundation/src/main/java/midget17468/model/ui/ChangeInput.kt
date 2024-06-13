package midget17468.model.ui

import midget17468.state.State

class ChangeInput<in T>(
    private val state: State<T>,
) {

    operator fun invoke(value: T) {
        state.update { value }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}