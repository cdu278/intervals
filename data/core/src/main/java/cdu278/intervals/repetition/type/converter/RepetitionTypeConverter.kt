package cdu278.intervals.repetition.type.converter

import androidx.room.TypeConverter
import midget17468.repetition.RepetitionType
import midget17468.repetition.RepetitionType.Password

class RepetitionTypeConverter {

    @TypeConverter
    fun toModel(data: String): RepetitionType {
        return when (data) {
            "password" -> Password
            else -> error("Invalid type: '$data'")
        }
    }

    @TypeConverter
    fun fromModel(type: RepetitionType): String {
        return when (type) {
            Password -> "password"
        }
    }
}