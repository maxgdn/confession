import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.confession.app.di.initKoin
import com.confession.app.service.RemarkViewModel
import com.confession.app.ui.main.MoodZone
import com.confession.app.ui.main.RemarkZone
import com.confession.app.ui.navigation.NavBar
import com.confession.app.ui.zones.Zone
import com.confession.app.ui.zones.Zones


fun main() = application {
    val app = initKoin()

    Window(
        onCloseRequest = { exitApplication() },
        title = "Confession",
        state = rememberWindowState(width = 300.dp, height = 300.dp)
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