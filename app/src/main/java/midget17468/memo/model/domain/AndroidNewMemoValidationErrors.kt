package midget17468.memo.model.domain

import android.content.res.Resources
import midget17468.computable.Computable
import midget17468.passs.R
import midget17468.string.computable.ResString

class AndroidNewMemoValidationErrors(
    private val resources: Resources,
) : NewMemoValidationErrors {

    override val emptyLabel: Computable<String>
        get() = ResString(resources, R.string.emptyLabel)

    override val emptyPassword: Computable<String>
        get() = ResString(resources, R.string.emptyPassword)

    override val passwordsDontMatch: Computable<String>
        get() = ResString(resources, R.string.passwordsDontMatch)
}