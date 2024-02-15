package midget17468.passs.room.typeConverter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class InstantConverter {

    @TypeConverter
    fun toLong(instant: Instant): Long = instant.toEpochMilliseconds()

    @TypeConverter
    fun fromLong(millis: Long): Instant = Instant.fromEpochMilliseconds(millis)
}