package cdu278.repetition.main.ui.activity

import android.os.Bundle
import android.os.Parcelable
import android.util.Base64
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.parcelize.Parcelize
import cdu278.IntervalsApplication
import cdu278.activity.dependent.DependentActivity
import cdu278.hash.s.Hashes
import cdu278.notification.channel.config.RepetitionsChannelConfig
import cdu278.notification.s.AndroidNotifications
import cdu278.permission.notification.notificationPermission
import cdu278.repetition.new.error.AndroidNewRepetitionValidationErrors
import cdu278.repetition.notification.s.AndroidRepetitionNotifications
import cdu278.repetition.root.ui.ScreenConfig
import cdu278.repetition.root.ui.component.RootComponent
import cdu278.repetition.root.ui.composable.Root
import cdu278.repetition.spaced.SpacedRepetitions
import cdu278.repetition.ui.composable.theme.PasssTheme

class MainActivity : DependentActivity<MainActivity.Deps>(Deps::Default) {

    interface Deps : Parcelable {

        val initialStack: List<ScreenConfig>
            get() = listOf(ScreenConfig.Main)

        @Parcelize
        class Default : Deps
    }

    companion object {

        var instance: MainActivity? = null
            private set
    }

    private val notificationPermission = notificationPermission()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        val appModule = IntervalsApplication.module

        val component =
            RootComponent(
                IntervalsComponentContext(
                    defaultComponentContext(),
                    appModule.db,
                    Hashes(
                        base64 = { String(Base64.encode(this, Base64.NO_WRAP)) },
                    ),
                    SpacedRepetitions(),
                    AndroidRepetitionNotifications(
                        context = this,
                        AndroidNotifications(
                            context = this,
                            notificationPermission,
                            channelConfig = RepetitionsChannelConfig(context = this),
                        ),
                    ),
                ),
                errors = AndroidNewRepetitionValidationErrors(resources),
                initialStack = { deps.initialStack },
            )

        setContent {
            PasssTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Root(component)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}