package midget17468.repetition.db.columnAdapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.json.Json
import midget17468.repetition.RepetitionData

internal class RepetitionDataColumnAdapter(
    private val json: Json
) : ColumnAdapter<RepetitionData, String> {

    override fun decode(databaseValue: String): RepetitionData {
        return json.decodeFromString(databaseValue)
    }

    override fun encode(value: RepetitionData): String {
        return json.encodeToString(RepetitionData.serializer(), value)
    }
}