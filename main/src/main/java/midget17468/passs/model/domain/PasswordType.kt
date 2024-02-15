package midget17468.passs.model.domain

sealed interface PasswordType {

    data class Sim(val operator: String) : PasswordType

    data class Password(val service: String) : PasswordType
}