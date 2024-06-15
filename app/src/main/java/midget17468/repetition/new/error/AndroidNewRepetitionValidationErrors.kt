package midget17468.repetition.new.error

import android.content.res.Resources
import midget17468.computable.Computable
import midget17468.passs.R
import midget17468.string.computable.ResString

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