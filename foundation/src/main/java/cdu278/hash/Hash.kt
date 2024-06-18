package cdu278.hash

import kotlinx.serialization.Serializable

@Serializable
class Hash(
    val value: String,
    val salt: String,
)