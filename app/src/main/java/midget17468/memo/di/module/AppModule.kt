package midget17468.memo.di.module

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import kotlinx.serialization.json.Json
import midget17468.memo.MemoDb
import midget17468.memo.repetitions.SpacedRepetitions
import midget17468.memo.sqldelight.db.MemoDb
import midget17468.notification.AndroidNotifications
import midget17468.permission.Permission

class AppModule(
    private val context: Context,
) {

    private val sqlDriver: SqlDriver
            by lazy { AndroidSqliteDriver(MemoDb.Schema, context, "memo.db") }

    val memoDb: MemoDb by lazy { MemoDb(sqlDriver, Json) }

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