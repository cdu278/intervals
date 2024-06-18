package cdu278.activity.dependent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import kotlin.reflect.KClass

abstract class DependentActivity<Deps : Parcelable>(
    private val defaultDeps: (() -> Deps)? = null,
) : ComponentActivity() {

    companion object {

        const val DEPS_KEY = "deps"

        fun <Deps : Parcelable, T : DependentActivity<Deps>> intent(
            context: Context,
            kClass: KClass<T>,
            deps: Deps,
        ): Intent {
            return Intent(context, kClass.java)
                .apply { putExtra(DEPS_KEY, deps) }
        }
    }

    lateinit var deps: Deps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("DEPRECATION")
        deps =
            intent
                .getParcelableExtra(DEPS_KEY)
                ?: defaultDeps
                    ?.invoke()
                        ?: error("No deps passed")
    }
}