package cdu278.repetition.matching

import cdu278.computable.Computable
import cdu278.repetition.RepetitionData

interface RepetitionDataMatching {

    suspend infix fun RepetitionData.matches(data: Computable<String>): Boolean
}