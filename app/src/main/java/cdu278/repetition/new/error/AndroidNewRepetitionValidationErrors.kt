package cdu278.repetition.new.error

import android.content.res.Resources
import cdu278.computable.Computable
import cdu278.intervals.R
import cdu278.repetition.new.error.owner.NewRepetitionValidationErrors
import cdu278.string.computable.ResString

class AndroidNewRepetitionValidationErrors(
    private val resources: Resources,
) : NewRepetitionValidationErrors {

    override val emptyLabel: Computable<String>
        get() = ResString(resources, R.string.emptyLabel)

    override val emptyPassword: Computable<String>
        get() = ResString(resources, R.string.emptyPassword)

    override val passwordsDontMatch: Computable<String>
        get() = ResString(resources, R.string.passwordsDontMatch)
}