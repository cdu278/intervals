package midget17468.memo.model.domain

class MemoItem(
    val id: Int,
    val type: MemoType,
    val label: String,
    val repetitionState: RepetitionState,
)