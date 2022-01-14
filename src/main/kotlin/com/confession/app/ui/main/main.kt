import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.confession.app.ResString
import com.confession.app.di.initKoin
import com.confession.app.ui.navigation.NavBar
import com.confession.app.ui.zones.Zones


fun main() = application {
    val app = initKoin()

    Window(
        onCloseRequest = { exitApplication() },
        title = ResString.appName,
        state = rememberWindowState(width = 1080.dp, height = 940.dp)
    ) {
        MaterialTheme {
            Zones(
                koinApplication = app
            ) {
                val zonesScope = this
                NavBar(zonesScope)
            }
        }
    }
}