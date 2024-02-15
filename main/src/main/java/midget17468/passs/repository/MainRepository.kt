package midget17468.passs.repository

import midget17468.passs.flowable.Flowable
import midget17468.passs.flowable.parametrized.password.RoomPasswords
import midget17468.passs.model.domain.Password
import midget17468.passs.room.dao.PasswordEntityDao

class MainRepository(
    private val dao: PasswordEntityDao,
) {

    val readItems: Flowable<List<Password>>
        get() = RoomPasswords(dao)
}