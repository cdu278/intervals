package midget17468.passs.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import midget17468.passs.room.entity.PasswordEntity

@Dao
abstract class PasswordEntityDao {

    @Query("SELECT * FROM password")
    abstract suspend fun all(): List<PasswordEntity>

    @Insert
    abstract suspend fun insert(passwordEntity: PasswordEntity)

    @Query("SELECT * FROM password WHERE id = :id")
    internal abstract fun findById(id: Int): PasswordEntity

    @Update
    internal abstract fun update(password: PasswordEntity)

    @Transaction
    open suspend fun update(id: Int, transform: (PasswordEntity) -> PasswordEntity) {
        val current = findById(id)
        update(transform(current))
    }
}