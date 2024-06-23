package cdu278.repetition.main.ui.activity

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import cdu278.IntervalsApplication
import cdu278.datetime.currentTime
import cdu278.hash.s.Hashes
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.notification.channel.config.RepetitionsChannelConfig
import cdu278.notification.s.AndroidNotifications
import cdu278.permission.notification.notificationPermission
import cdu278.repetition.notification.s.AndroidRepetitionNotifications
import cdu278.repetition.root.main.ui.composasble.theme.IntervalsTheme
import cdu278.repetition.root.ui.ScreenConfig
import cdu278.repetition.root.ui.component.RootComponent
import cdu278.repetition.root.ui.composable.Root
import cdu278.repetition.s.repository.RoomRepetitionsRepository
import cdu278.repetition.spaced.SpacedRepetitions
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone

class MainActivity : ComponentActivity() {

    companion object {

        var instance: MainActivity? = null
            private set
    }

    private val notificationPermission = notificationPermission()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        val appModule = IntervalsApplication.module

        val repetitionNotifications =
            AndroidRepetitionNotifications(
                context = this,
                AndroidNotifications(
                    context = this,
                    notificationPermission,
                    channelConfig = RepetitionsChannelConfig(context = this),
                ),
            )

        val component =
            RootComponent(
                IntervalsComponentContext(
                    defaultComponentContext(),
                    Hashes(
                        base64 = { String(Base64.encode(this, Base64.NO_WRAP)) },
                    ),
                    SpacedRepetitions(),
                    repetitionNotifications,
                    currentTime = { Clock.System.currentTime(TimeZone.currentSystemDefault()) },
                ),
                repetitionsRepositoryFactory = {
                    RoomRepetitionsRepository(
                        appModule.db,
                    )
                },
                initialStack = { listOf(ScreenConfig.Main) },
            )

        setContent {
            IntervalsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Root(component)
                }
            }
        }

        lifecycle.coroutineScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                repetitionNotifications.remove()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}