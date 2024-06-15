package midget17468.repetition.di.module

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import kotlinx.serialization.json.Json
import midget17468.repetition.spaced.SpacedRepetitions
import midget17468.repetition.db.RepetitionDb
import midget17468.notification.s.AndroidNotifications
import midget17468.permission.Permission
import midget17468.repetition.RepetitionDb

class AppModule(
    private val context: Context,
) {

    private val sqlDriver: SqlDriver
            by lazy { AndroidSqliteDriver(RepetitionDb.Schema, context, "db") }

    val db: RepetitionDb by lazy { RepetitionDb(sqlDriver, Json) }

    fun notificationsFactory(permission: Permission): AndroidNotifications.Factory {
        return AndroidNotifications.Factory(
            context,
            NotificationManagerCompat.from(context),
            permission,
        )
    }

    val spacedRepetitions: SpacedRepetitions
        get() = SpacedRepetitions()
}