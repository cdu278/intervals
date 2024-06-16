package midget17468.scope.owner

import midget17468.scope.Scope

interface ScopeOwner<Component, T : Scope> {

    fun <R> scope(block: T.() -> R): R

    fun bind(component: Component)
}