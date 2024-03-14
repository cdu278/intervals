package midget17468.memo.sqldelight.db

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import kotlinx.serialization.json.Json
import midget17468.memo.Memo
import midget17468.memo.MemoDb
import midget17468.memo.sqldelight.columnAdapter.MemoDataColumnAdapter
import midget17468.memo.sqldelight.columnAdapter.RepetitionStateColumnAdapter

fun MemoDb(driver: SqlDriver, json: Json): MemoDb {
    return MemoDb(
        driver,
        Memo.Adapter(
            idAdapter = IntColumnAdapter,
            typeAdapter = EnumColumnAdapter(),
            memoDataAdapter = MemoDataColumnAdapter(json),
            repetitionStateAdapter = RepetitionStateColumnAdapter(json),
        ),
    )
}