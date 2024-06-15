package midget17468

import android.app.Application
import midget17468.repetition.di.module.AppModule

class MemoApplication : Application() {

    val module = AppModule(this)

    companion object {

        private lateinit var instance: MemoApplication

        val module: AppModule
            get() = instance.module
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}