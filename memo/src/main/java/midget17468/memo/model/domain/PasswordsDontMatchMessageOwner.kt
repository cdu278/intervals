package midget17468.memo.model.domain

import midget17468.computable.Computable

interface PasswordsDontMatchMessageOwner {

    val passwordsDontMatch: Computable<String>
}