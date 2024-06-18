package cdu278.repetition.data.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import cdu278.repetition.RepetitionData

class RepetitionDataConverter {

    private val json: Json
        get() = Json

    @TypeConverter
    fun toModel(data: String): RepetitionData {
        return json.decodeFromString(data)
    }

    @TypeConverter
    fun fromModel(data: RepetitionData): String {
        return json.encodeToString(RepetitionData.serializer(), data)
    }
}