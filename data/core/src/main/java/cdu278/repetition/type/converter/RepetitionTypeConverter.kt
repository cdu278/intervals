package cdu278.repetition.type.converter

import androidx.room.TypeConverter
import cdu278.repetition.RepetitionType
import cdu278.repetition.RepetitionType.Password

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