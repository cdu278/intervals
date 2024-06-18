package cdu278.repetition.scope

import android.content.Context
import cdu278.db.IntervalsDb
import cdu278.db.createDb

class AppScope(
    private val context: Context,
) {

    val db: IntervalsDb by lazy { context.createDb() }
}