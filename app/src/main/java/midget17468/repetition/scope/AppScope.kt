package midget17468.repetition.scope

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import kotlinx.serialization.json.Json
import midget17468.repetition.RepetitionDb
import midget17468.repetition.db.RepetitionDb

class AppScope(
    private val context: Context,
) {

    private val sqlDriver: SqlDriver
            by lazy { AndroidSqliteDriver(RepetitionDb.Schema, context, "db") }

    val db: RepetitionDb by lazy { RepetitionDb(sqlDriver, Json) }
}