package midget17468.repetition.scope

import android.content.Context
import cdu278.intervals.db.IntervalsDb
import cdu278.intervals.db.createDb

class AppScope(
    private val context: Context,
) {

    val db: IntervalsDb by lazy { context.createDb() }
}