package cdu278.repetition.root.main.tab

import cdu278.computable.Computable
import cdu278.predicate.Predicate
import cdu278.repetition.item.RepetitionItem
import cdu278.repetition.root.main.tab.filter.ActiveRepetitionsFilter
import cdu278.repetition.root.main.tab.filter.ActualRepetitionsFilter
import cdu278.repetition.root.main.tab.filter.ArchivedRepetitionsFilter
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
enum class MainTab {

    Actual {

        override fun repetitionsFilter(
            currentTime: Computable<LocalDateTime>
        ): Predicate<RepetitionItem> {
            return ActualRepetitionsFilter(currentTime)
        }
    },
    Active {

        override fun repetitionsFilter(
            currentTime: Computable<LocalDateTime>
        ): Predicate<RepetitionItem> {
            return ActiveRepetitionsFilter(currentTime)
        }
    },
    Archive {

        override fun repetitionsFilter(
            currentTime: Computable<LocalDateTime>
        ): Predicate<RepetitionItem> {
            return ArchivedRepetitionsFilter()
        }
    };

    abstract fun repetitionsFilter(
        currentTime: Computable<LocalDateTime>
    ): Predicate<RepetitionItem>
}