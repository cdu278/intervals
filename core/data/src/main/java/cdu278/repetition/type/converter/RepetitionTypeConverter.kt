package cdu278.repetition.type.converter

import androidx.room.TypeConverter
import cdu278.repetition.RepetitionType
import cdu278.repetition.RepetitionType.Email
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.RepetitionType.Pin

class RepetitionTypeConverter {

    @TypeConverter
    fun toModel(data: String): RepetitionType {
        return when (data) {
            "password" -> Password
            "pin" -> Pin
            "email" -> Email
            else -> error("Invalid type: '$data'")
        }
    }

    @TypeConverter
    fun fromModel(type: RepetitionType): String {
        return when (type) {
            Password -> "password"
            Pin -> "pin"
            Email -> "email"
        }
    }
}