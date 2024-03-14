package midget17468.computable.parametrized

fun interface ParametrizedComputable<in P, out T> : (P) -> T