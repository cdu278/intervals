package midget17468.passs.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import midget17468.passs.model.domain.NoTypePassword

@Entity(tableName = "password")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    @ColumnInfo(name = "type")
    val typeJson: String,
    override val hash: String,
    @ColumnInfo("next_check_date")
    val nextCheckInstant: Instant,
) : NoTypePassword {

    override val nextCheckDate: LocalDateTime
        get() = nextCheckInstant.toLocalDateTime(TimeZone.currentSystemDefault())
}