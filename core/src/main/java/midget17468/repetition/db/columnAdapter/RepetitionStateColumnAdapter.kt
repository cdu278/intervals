package midget17468.repetition.db.columnAdapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.json.Json
import midget17468.repetition.RepetitionState

internal class RepetitionStateColumnAdapter(
    private val json: Json
) : ColumnAdapter<RepetitionState, String> {

    override fun decode(databaseValue: String): RepetitionState {
        return json.decodeFromString(RepetitionState.serializer(), databaseValue)
    }

    override fun encode(value: RepetitionState): String {
        return json.encodeToString(RepetitionState.serializer(), value)
    }
}