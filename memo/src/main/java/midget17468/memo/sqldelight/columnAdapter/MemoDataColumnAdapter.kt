package midget17468.memo.sqldelight.columnAdapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.json.Json
import midget17468.memo.data.MemoData

internal class MemoDataColumnAdapter(
    private val json: Json
) : ColumnAdapter<MemoData, String> {

    override fun decode(databaseValue: String): MemoData {
        return json.decodeFromString(databaseValue)
    }

    override fun encode(value: MemoData): String {
        return json.encodeToString(MemoData.serializer(), value)
    }
}