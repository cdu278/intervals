package cdu278.repetition

import kotlinx.serialization.Serializable

@Serializable
enum class RepetitionType {

    Password,
    Pin,
    Email,
    Phone,
}