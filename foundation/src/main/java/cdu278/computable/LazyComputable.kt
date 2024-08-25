package cdu278.computable

class LazyComputable<T>(
    private val lazy: Lazy<T>
) : Computable<T> {

    override fun invoke(): T = lazy.value
}