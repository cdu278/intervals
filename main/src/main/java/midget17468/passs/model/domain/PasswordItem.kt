package midget17468.passs.model.domain

import kotlinx.datetime.LocalDateTime

class PasswordItem(
    val id: Int,
    val type: PasswordType,
    val nextCheckDate: LocalDateTime,
)