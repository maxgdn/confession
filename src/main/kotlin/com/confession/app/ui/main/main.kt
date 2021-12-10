import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.confession.app.di.initKoin
import com.confession.app.meter.MoodMeter
import com.confession.app.repository.RemarkRepository
import com.confession.app.service.RemarkViewModel
import com.confession.app.ui.mood.MoodMeterGraph
import kotlinx.coroutines.launch



fun main() = application {
    var app = initKoin()


    Window(
        onCloseRequest = { exitApplication() },
        title = "Compose for Desktop",
        state = rememberWindowState(width = 300.dp, height = 300.dp)
    ) {
        val remarkViewModel = app.koin.get<RemarkViewModel>()

        MaterialTheme {
            val scope = rememberCoroutineScope()
            val questionTextValue = remember {mutableStateOf(TextFieldValue())}
            val answerTextValue = remember {mutableStateOf(TextFieldValue())}
            val remarkState = remarkViewModel.remarkState.collectAsState()

            Column {
               Row {

                   TextField(
                       value = questionTextValue.value,
                       onValueChange = {
                           questionTextValue.value = it
                       }
                   )

                   TextField(
                       value = answerTextValue.value,
                       onValueChange = {
                           answerTextValue.value = it
                       }
                   )

                   Button(
                       onClick = {
                           //submit
                           scope.launch {
                               remarkViewModel.createRemark(
                                   question = questionTextValue.value.text,
                                   answer = answerTextValue.value.text
                               )
                               questionTextValue.value = TextFieldValue()
                               answerTextValue.value = TextFieldValue()
                           }
                        }
                   ) {
                       Text("Create")
                   }
               }

                Row {
                    Button(onClick = {
                        scope.launch {
                            println("updating")
                            remarkViewModel.fetchAll()
                        }
                    }) {
                        Text("Update")
                    }
               }

                remarkState.value.data?.forEach {
                    Row {
                        Text(it.id)
                        Spacer(Modifier.size(2.dp))
                        Text(it.question)
                        Spacer(Modifier.size(2.dp))
                        Text(it.answer)
                    }
                }

                MoodMeterGraph(moodMeter = MoodMeter())
            }
        }
    }
}