package midget17468.passs.model.ui

sealed interface UiPasswordList {

    object Loading : UiPasswordList

    class Loaded(
        val items: List<UiPasswordItem>,
    ) : UiPasswordList
}