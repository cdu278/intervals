package midget17468.memo.model.ui

import midget17468.model.ui.UiInput

data class UiNewMemo(
    val label: UiInput<String>,
    val data: UiNewMemoData,
    val hint: UiInput<String>,
    val error: String? = null,
)