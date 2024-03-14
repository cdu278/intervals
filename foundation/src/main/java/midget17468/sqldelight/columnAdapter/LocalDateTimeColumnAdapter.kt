package midget17468.sqldelight.columnAdapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json

class LocalDateTimeColumnAdapter(
    private val json: Json
) : ColumnAdapter<LocalDateTime, String> {

    override fun decode(databaseValue: String): LocalDateTime {
        return json.decodeFromString(LocalDateTime.serializer(), databaseValue)
    }

    override fun encode(value: LocalDateTime): String {
        return json.encodeToString(LocalDateTime.serializer(), value)
    }
}