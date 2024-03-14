package midget17468.memo.model.ui

sealed interface NextRepetitionDate {

    data object Today : NextRepetitionDate

    data object Tomorrow : NextRepetitionDate

    data class DayOfMonth(
        val month: Int,
        val day: Int,
    ) : NextRepetitionDate
}