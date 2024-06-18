package cdu278.repetition.state.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import cdu278.repetition.RepetitionState

class RepetitionStateConverter {

    private val json
        get() = Json

    @TypeConverter
    fun toModel(data: String): RepetitionState {
        return json.decodeFromString(data)
    }

    @TypeConverter
    fun fromModel(state: RepetitionState): String {
        return json.encodeToString(RepetitionState.serializer(), state)
    }
}