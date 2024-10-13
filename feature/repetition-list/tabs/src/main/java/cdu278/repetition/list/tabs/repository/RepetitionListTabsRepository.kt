package cdu278.repetition.list.tabs.repository

import cdu278.repetition.RepetitionType
import kotlinx.coroutines.flow.Flow

interface RepetitionListTabsRepository {

    val presentRepetitionTypesFlow: Flow<List<RepetitionType>>
}