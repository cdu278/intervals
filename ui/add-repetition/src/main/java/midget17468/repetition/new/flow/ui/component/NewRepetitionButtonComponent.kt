package midget17468.repetition.new.flow.ui.component

class NewRepetitionButtonComponent(
    private val openEditor: () -> Unit,
) {

    fun click() = openEditor()
}