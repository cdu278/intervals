package midget17468.passs.flowable.parametrized.password

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import midget17468.passs.flowable.Flowable
import midget17468.passs.model.domain.NoTypePassword
import midget17468.passs.model.domain.Password
import midget17468.passs.model.domain.PasswordType
import midget17468.passs.room.dao.PasswordEntityDao
import midget17468.passs.room.entity.PasswordEntity

class RoomPasswords(
    private val entityDao: PasswordEntityDao,
) : Flowable<List<Password>> {

    override fun invoke(): Flow<List<Password>> {
        return flow {
            emit(entityDao.all().map(::EntityPassword))
        }
    }

    private class EntityPassword(
        entity: PasswordEntity,
    ) : Password,
        NoTypePassword by entity {

        override val type: PasswordType
            get() = PasswordType.Sim(operator = "Venik")
    }
}