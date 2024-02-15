package midget17468.passs.model.domain

import kotlinx.datetime.LocalDateTime

interface NoTypePassword {

    val id: Int

    val hash: String

    val nextCheckDate: LocalDateTime
}