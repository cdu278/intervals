package midget17468.repetition.ui.activity

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
import midget17468.MemoApplication
import midget17468.activity.dependent.DependentActivity
import midget17468.computable.Computable
import midget17468.hash.s.Hashes
import midget17468.notification.channel.config.RepetitionsChannelConfig
import midget17468.permission.notification.notificationPermission
import midget17468.repetition.R
import midget17468.repetition.RepetitionDb
import midget17468.repetition.matching.RepetitionDataMatching
import midget17468.repetition.new.error.owner.EmptyPasswordErrorOwner
import midget17468.repetition.notification.s.AndroidRepetitionNotifications
import midget17468.repetition.s.repository.RepetitionsRepositoryInstance
import midget17468.repetition.ui.component.RepetitionComponent
import midget17468.repetition.ui.composable.RepetitionScreen
import midget17468.repetition.ui.composable.theme.PasssTheme
import midget17468.string.computable.ResString

class RepetitionActivity : DependentActivity<RepetitionActivity.Deps>() {

    interface Deps : Parcelable {

        val repetitionId: Int

        val db: RepetitionDb

        val hashes: Hashes

        val close: (Activity) -> (() -> Unit)
    }

    private class Errors(
        private val resources: Resources,
    ) : EmptyPasswordErrorOwner {

        override val emptyPassword: Computable<String>
            get() = ResString(resources, R.string.repetition_emptyPassword)
    }

    private val notificationPermission = notificationPermission()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val componentContext = defaultComponentContext()

        val repository =
            componentContext.instanceKeeper.getOrCreate {
                RepetitionsRepositoryInstance(deps.db.repetitionQueries)
            }

        val appModule = MemoApplication.module

        val component =
            RepetitionComponent(
                componentContext,
                deps.repetitionId,
                repository,
                AndroidRepetitionNotifications(
                    applicationContext,
                    appModule
                        .notificationsFactory(notificationPermission)
                        .create(RepetitionsChannelConfig(this)),
                ),
                Errors(resources),
                appModule.spacedRepetitions,
                close = deps.close(this),
                dataMatching = RepetitionDataMatching(deps.hashes),
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