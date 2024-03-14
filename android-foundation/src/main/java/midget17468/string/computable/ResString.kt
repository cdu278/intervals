package midget17468.string.computable

import android.content.res.Resources
import androidx.annotation.StringRes
import midget17468.computable.Computable

class ResString(
    private val resources: Resources,
    @StringRes
    private val resId: Int,
) : Computable<String> {

    override fun invoke(): String = resources.getString(resId)
}