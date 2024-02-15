package midget17468.passs.room.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import midget17468.passs.decompose.context.extended.ExtendedComponentContext
import midget17468.passs.decompose.instance.RoomDatabaseInstance
import midget17468.passs.decompose.instance.retainedCoroutineScope
import midget17468.passs.room.dao.PasswordEntityDao
import midget17468.passs.room.entity.PasswordEntity
import midget17468.passs.room.typeConverter.InstantConverter
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Database(
    entities = [
        PasswordEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    InstantConverter::class,
)
abstract class PasswordDb : RoomDatabase() {

    abstract val passwordEntityDao: PasswordEntityDao
}

val ExtendedComponentContext.passwordDb: PasswordDb
    get() = instanceKeeper.getOrCreate("passwordDb") {
        RoomDatabaseInstance(
            Room
                .inMemoryDatabaseBuilder(context, PasswordDb::class.java)
                .build()
                .also { db ->
                    retainedCoroutineScope.launch {
                        db.passwordEntityDao.run {
                            val now = Clock.System.now()
                            insert(
                                PasswordEntity(
                                    typeJson = "",
                                    hash = "aedvh8293refu",
                                    nextCheckInstant = now + 2.toDuration(DurationUnit.HOURS),
                                )
                            )
                            insert(
                                PasswordEntity(
                                    typeJson = "",
                                    hash = "aedvh8293refu",
                                    nextCheckInstant = now + 8.toDuration(DurationUnit.HOURS),
                                )
                            )
                            insert(
                                PasswordEntity(
                                    typeJson = "",
                                    hash = "aedvh8293refu",
                                    nextCheckInstant = now + 2.toDuration(DurationUnit.DAYS),
                                )
                            )
                        }
                    }
                }
        )
    }.db