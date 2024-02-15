package midget17468.passs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import midget17468.passs.compose.composable.MainScreen
import midget17468.passs.decompose.component.MainComponent
import midget17468.passs.directions.MainDirections
import midget17468.passs.main.nextCheckDate.computable.ComputableNextCheckDate
import midget17468.passs.repository.MainRepository
import midget17468.passs.ui.theme.PasssTheme

class MainActivity : ComponentActivity() {

    private val componentContext: ComponentContext
            by lazy(LazyThreadSafetyMode.NONE) { defaultComponentContext() }

    private val component
            by lazy(LazyThreadSafetyMode.NONE) {
                MainComponent(
                    componentContext,
                    MainRepository(),
                    MainDirections(),
                    ComputableNextCheckDate(
                        now = {
                            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                        }
                    ),
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasssTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(component)
                }
            }
        }
    }
}