package cdu278

import android.app.Application
import cdu278.repetition.scope.AppScope

class IntervalsApplication : Application() {

    val module = AppScope(this)

    companion object {

        private lateinit var instance: IntervalsApplication

        val module: AppScope
            get() = instance.module
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}