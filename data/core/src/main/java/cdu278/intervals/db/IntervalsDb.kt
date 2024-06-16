package cdu278.intervals.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cdu278.intervals.repetition.data.converter.RepetitionDataConverter
import cdu278.intervals.repetition.db.dao.RepetitionDao
import cdu278.intervals.repetition.db.entity.RepetitionEntity
import cdu278.intervals.repetition.notification.db.dao.RepetitionNotificationDao
import cdu278.intervals.repetition.s.db.dao.RepetitionsDao
import cdu278.intervals.repetition.state.converter.RepetitionStateConverter
import cdu278.intervals.repetition.type.converter.RepetitionTypeConverter

@Database(
    entities = [
        RepetitionEntity::class,
    ],
    version = 1
)
@TypeConverters(
    RepetitionTypeConverter::class,
    RepetitionDataConverter::class,
    RepetitionStateConverter::class,
)
abstract class IntervalsDb : RoomDatabase() {

    abstract val repetitionsDao: RepetitionsDao

    abstract val repetitionDao: RepetitionDao

    abstract val repetitionNotificationDao: RepetitionNotificationDao
}

fun Context.createDb(): IntervalsDb {
    return Room
        .databaseBuilder(this, IntervalsDb::class.java, "db")
        .build()
}