package midget17468.passs

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.parcelize.Parcelize
import midget17468.activity.dependent.DependentActivity
import midget17468.hash.algorithm.HashAlgorithm
import midget17468.memo.MemoDb
import midget17468.memo.activity.RepetitionActivity
import midget17468.memo.application.MemoApplication
import midget17468.memo.model.domain.AndroidNewMemoValidationErrors
import midget17468.memo.model.domain.NewMemoValidationErrors
import midget17468.memo.repetitions.notifications.AndroidRepetitionNotifications
import midget17468.notification.channel.config.RepetitionsChannelConfig
import midget17468.passs.compose.composable.MainScreen
import midget17468.passs.decompose.component.MainComponent
import midget17468.passs.ui.theme.PasssTheme
import midget17468.permission.notification.notificationPermission

class MainActivity : ComponentActivity() {

    companion object {

        var instance: MainActivity? = null
            private set
    }

    private val notificationPermission = notificationPermission()

    private class Errors(
        resources: Resources,
    ) : NewMemoValidationErrors by AndroidNewMemoValidationErrors(resources)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        val appModule = MemoApplication.module

        val component =
            MainComponent(
                defaultComponentContext(),
                appModule.memoDb,
                Errors(resources),
                AndroidRepetitionNotifications(
                    context = this,
                    appModule
                        .notificationsFactory(notificationPermission)
                        .create(RepetitionsChannelConfig(this)),
                ),
                HashAlgorithm.Simple,
                repeatMemo = { memoId ->
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
        override val memoId: Int,
    ) : RepetitionActivity.Deps {

        override val memoDb: MemoDb
            get() = MemoApplication.module.memoDb

        override val close: (Activity) -> (() -> Unit)
            get() = { activity ->
                { activity.finish() }
            }
    }
}