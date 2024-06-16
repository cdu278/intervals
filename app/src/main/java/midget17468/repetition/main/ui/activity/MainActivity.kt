package midget17468.repetition.main.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.parcelize.Parcelize
import midget17468.MemoApplication
import midget17468.activity.dependent.DependentActivity
import midget17468.hash.s.Hashes
import midget17468.hash.s.repository.AndroidHashesRepository
import midget17468.hash.salt.hashSaltDataStore
import midget17468.notification.channel.config.RepetitionsChannelConfig
import midget17468.permission.notification.notificationPermission
import midget17468.repetition.RepetitionDb
import midget17468.repetition.main.ui.component.MainComponent
import midget17468.repetition.main.ui.composable.MainScreen
import midget17468.repetition.new.error.AndroidNewRepetitionValidationErrors
import midget17468.repetition.notification.s.AndroidRepetitionNotifications
import midget17468.repetition.ui.activity.RepetitionActivity
import midget17468.repetition.ui.composable.theme.PasssTheme

class MainActivity : ComponentActivity() {

    companion object {

        var instance: MainActivity? = null
            private set
    }

    private val notificationPermission = notificationPermission()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        val appModule = MemoApplication.module

        val component =
            MainComponent(
                defaultComponentContext(),
                appModule.db,
                AndroidNewRepetitionValidationErrors(resources),
                AndroidRepetitionNotifications(
                    context = this,
                    appModule
                        .notificationsFactory(notificationPermission)
                        .create(RepetitionsChannelConfig(this)),
                ),
                Hashes(
                    AndroidHashesRepository(hashSaltDataStore),
                ),
                repeat = { memoId ->
                    startActivity(
                        DependentActivity.intent(
                            this,
                            RepetitionActivity::class,
                            RepetitionDeps(memoId)
                        )
                    )
                },
                appModule.spacedRepetitions,
            )

        setContent {
            PasssTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen(component)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    @Parcelize
    private class RepetitionDeps(
        override val repetitionId: Int,
    ) : RepetitionActivity.Deps {

        override val db: RepetitionDb
            get() = MemoApplication.module.db

        override val hashes: Hashes
            get() = MemoApplication.module.hashes

        override val close: (Activity) -> (() -> Unit)
            get() = { activity ->
                { activity.finish() }
            }
    }
}