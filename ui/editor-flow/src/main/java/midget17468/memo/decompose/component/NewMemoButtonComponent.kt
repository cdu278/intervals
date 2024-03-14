package midget17468.memo.decompose.component

class NewMemoButtonComponent(
    private val openEditor: () -> Unit,
) {

    fun click() = openEditor()
}