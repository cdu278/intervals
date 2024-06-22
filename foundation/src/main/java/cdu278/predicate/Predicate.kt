package cdu278.predicate

fun interface Predicate<in T> {

    suspend fun test(value: T): Boolean
}