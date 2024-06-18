package cdu278.repetition.new.error.owner

import cdu278.computable.Computable

interface EmptyPasswordErrorOwner {

    val emptyPassword: Computable<String>
}