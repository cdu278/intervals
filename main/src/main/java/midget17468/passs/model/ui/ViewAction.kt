package midget17468.passs.model.ui

class ViewAction(
    action: () -> Unit
) : () -> Unit by action {

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int = javaClass.hashCode()
}