package cdu278.ui.action

class UiAction(
    private val key: Any?,
    action: () -> Unit
) : () -> Unit by action {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UiAction

        return key == other.key
    }

    override fun hashCode(): Int = key?.hashCode() ?: 0
}