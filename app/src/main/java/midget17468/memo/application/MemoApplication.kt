package midget17468.memo.application

import android.app.Application
import midget17468.memo.di.module.AppModule

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