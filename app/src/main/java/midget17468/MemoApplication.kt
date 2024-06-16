package midget17468

import android.app.Application
import midget17468.repetition.scope.AppScope

class MemoApplication : Application() {

    val module = AppScope(this)

    companion object {

        private lateinit var instance: MemoApplication

        val module: AppScope
            get() = instance.module
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}