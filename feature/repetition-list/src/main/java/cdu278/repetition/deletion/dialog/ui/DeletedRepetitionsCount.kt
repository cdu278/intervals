package cdu278.repetition.deletion.dialog.ui

internal sealed interface DeletedRepetitionsCount {

    data object Single : DeletedRepetitionsCount

    data class Multiple(val value: Int) : DeletedRepetitionsCount
}