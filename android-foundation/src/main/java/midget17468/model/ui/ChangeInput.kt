package midget17468.model.ui

import midget17468.input.Input

class ChangeInput<in T>(
    private val input: Input<T>,
) {

    operator fun invoke(value: T) {
        input.update { value }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}