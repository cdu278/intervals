package midget17468.scope.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import midget17468.computable.Computable
import midget17468.scope.Scope

class ViewModelScopeOwner<Component : ViewModelStoreOwner, T : Scope>(
    private val scopeFactory: (Computable<Component>) -> (() -> T)
) : ScopeOwner<Component, T> {

    private var _component: Component? = null
    private val component
        get() = _component!!

    override fun bind(component: Component) {
        if (component == _component) return
        _component = component
        getOrCreateViewModel()
    }

    private fun getOrCreateViewModel(): ScopeViewModel<T> {
        @Suppress("UNCHECKED_CAST")
        return ViewModelProvider(
            component,
            ScopeViewModel.Factory(
                scopeOwner = this,
                scopeFactory(Computable { component }),
            )
        )[ScopeViewModel::class.java] as ScopeViewModel<T>
    }

    override fun <R> scope(block: T.() -> R): R {
        return getOrCreateViewModel().scope.block()
    }

    private class ScopeViewModel<T : Scope>(
        private val scopeOwner: ViewModelScopeOwner<*, T>,
        scopeFactory: () -> T
    ) : ViewModel() {

        val scope by lazy(scopeFactory)

        override fun onCleared() {
            super.onCleared()
            scopeOwner._component = null
            scope.close()
        }

        class Factory<S : Scope>(
            private val scopeOwner: ViewModelScopeOwner<*, S>,
            private val scopeFactory: () -> S
        ) : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ScopeViewModel(scopeOwner, scopeFactory) as T
            }
        }
    }
}