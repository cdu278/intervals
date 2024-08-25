package cdu278.phone.formatted

import cdu278.computable.Computable
import cdu278.computable.LazyComputable

@Suppress("FunctionName")
fun FormattedPhone(
    srcPhone: String
): Computable<String> {
    return LazyComputable(lazy {
        srcPhone.replace(Regex("[ \\-()]"), "")
    })
}