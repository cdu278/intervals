package cdu278.receiver.broadcast.dependent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import kotlin.reflect.KClass

abstract class DependentBroadcastReceiver<Deps : Parcelable> : BroadcastReceiver() {

    companion object {

        private const val DEPS_KEY = "deps"

        fun <Deps : Parcelable, T : DependentBroadcastReceiver<Deps>> Context.intent(
            kClass: KClass<T>,
            action: String,
            deps: Deps,
        ): Intent {
            return Intent(
                this,
                kClass.java,
            ).also {
                it.action = action
                it.putExtra(DEPS_KEY, deps)
            }
        }
    }

    @Suppress("DEPRECATION")
    val Intent.deps: Deps
        get() = getParcelableExtra(DEPS_KEY)!!
}