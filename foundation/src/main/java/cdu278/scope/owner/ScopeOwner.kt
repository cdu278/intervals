package cdu278.scope.owner

import cdu278.scope.Scope

interface ScopeOwner<Component, T : Scope> {

    fun <R> scope(block: T.() -> R): R

    fun bind(component: Component)
}