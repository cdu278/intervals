package midget17468.memo.activity

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import midget17468.activity.dependent.DependentActivity
import midget17468.computable.Computable
import midget17468.memo.MemoDb
import midget17468.memo.application.MemoApplication
import midget17468.memo.compose.composable.RepetitionScreen
import midget17468.memo.decompose.component.RepetitionComponent
import midget17468.memo.decompose.instance.MemoRepositoryInstance
import midget17468.memo.model.domain.EmptyPasswordMessageOwner
import midget17468.memo.repetitions.SpacedRepetitions
import midget17468.memo.repetitions.notifications.AndroidRepetitionNotifications
import midget17468.notification.channel.config.RepetitionsChannelConfig
import midget17468.passs.ui.theme.PasssTheme
import midget17468.permission.notification.notificationPermission
import midget17468.repetition.R
import midget17468.string.computable.ResString

class RepetitionActivity : DependentActivity<RepetitionActivity.Deps>() {

    interface Deps : Parcelable {

        val memoId: Int

        val memoDb: MemoDb

        val close: (Activity) -> (() -> Unit)
    }

    private class Errors(
        private val resources: Resources,
    ) : EmptyPasswordMessageOwner {

        override val emptyPassword: Computable<String>
            get() = ResString(resources, R.string.repetition_emptyPassword)
    }

    private val notificationPermission = notificationPermission()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val componentContext = defaultComponentContext()

        val repository =
            componentContext.instanceKeeper.getOrCreate {
                MemoRepositoryInstance(deps.memoDb.memoQueries)
            }

        val appModule = MemoApplication.module

        val component =
            RepetitionComponent(
                componentContext,
                deps.memoId,
                repository,
                AndroidRepetitionNotifications(
                    applicationContext,
                    appModule
                        .notificationsFactory(notificationPermission)
                        .create(RepetitionsChannelConfig(this)),
                ),
                Errors(resources),
                spacedRepetitions = SpacedRepetitions(appModule.spaceRepetitionStrategy),
                close = deps.close(this),
            )

        setContent {
            PasssTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    RepetitionScreen(component)
                }
            }
        }
    }
}