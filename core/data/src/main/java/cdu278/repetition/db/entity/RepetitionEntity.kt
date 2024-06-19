package cdu278.repetition.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import cdu278.repetition.Repetition
import cdu278.repetition.RepetitionData
import cdu278.repetition.RepetitionState
import cdu278.repetition.RepetitionType

@Entity(tableName = RepetitionEntity.TABLE_NAME)
data class RepetitionEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,
    override val label: String,
    override val type: RepetitionType,
    override val data: RepetitionData,
    override val state: RepetitionState,
    override val hint: String?,
) : Repetition {

    companion object {

        const val TABLE_NAME = "repetition"

        fun Repetition.asEntity(): RepetitionEntity {
            return RepetitionEntity(
                id,
                label,
                type,
                data,
                state,
                hint,
            )
        }
    }
}