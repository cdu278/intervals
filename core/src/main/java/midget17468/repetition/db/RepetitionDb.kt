package midget17468.repetition.db

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import kotlinx.serialization.json.Json
import midget17468.repetition.Repetition
import midget17468.repetition.RepetitionDb
import midget17468.repetition.db.columnAdapter.RepetitionDataColumnAdapter
import midget17468.repetition.db.columnAdapter.RepetitionStateColumnAdapter

fun RepetitionDb(driver: SqlDriver, json: Json): RepetitionDb {
    return RepetitionDb(
        driver,
        Repetition.Adapter(
            idAdapter = IntColumnAdapter,
            typeAdapter = EnumColumnAdapter(),
            repetitionDataAdapter = RepetitionDataColumnAdapter(json),
            repetitionStateAdapter = RepetitionStateColumnAdapter(json),
        ),
    )
}