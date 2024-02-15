package midget17468.passs.decompose.instance

import androidx.room.RoomDatabase
import com.arkivanov.essenty.instancekeeper.InstanceKeeper

class RoomDatabaseInstance<T : RoomDatabase>(
    val db: T
) : InstanceKeeper.Instance {

    override fun onDestroy() {
        db.close()
    }
}