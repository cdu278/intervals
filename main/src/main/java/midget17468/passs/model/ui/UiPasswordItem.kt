package midget17468.passs.model.ui

import midget17468.passs.model.domain.PasswordType

data class UiPasswordItem(
    val id: Int,
    val type: PasswordType,
    val nextCheckDate: NextCheckDate,
    val open: ViewAction,
) {

    sealed interface NextCheckDate {

        object Today : NextCheckDate

        object Tomorrow : NextCheckDate

        data class DayOfMonth(
            val month: Int,
            val day: Int,
        ) : NextCheckDate
    }
}