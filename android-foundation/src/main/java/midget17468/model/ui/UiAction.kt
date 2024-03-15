package midget17468.model.ui

class UiAction(
    action: () -> Unit
) : () -> Unit by action {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()

    companion object {

        val NoOp: UiAction = UiAction {  }
    }
}