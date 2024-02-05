package com.confession.app.ui.main.zones

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.confession.app.ResString
import com.confession.app.meter.MoodMeterElement
import com.confession.app.meter.MoodMeterGraphics
import com.confession.app.model.ConfessionResponse
import com.confession.app.service.ConfessionViewModel
import com.confession.app.util.howCanYouFeelFormattedText
import com.confession.app.util.understandingFormattedText
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime

@Composable
fun PreviewMoodImage(
    confessionResponse: ConfessionResponse,
    moodMeterElement: MoodMeterElement?
) {
    Row {
        val imageBitmap: MutableState<ImageBitmap?> = remember { mutableStateOf(null) }

        LaunchedEffect(confessionResponse) {
            val bitmap = MoodMeterGraphics.getMoodElementAsImageBitmap(
                moodMeterElement,
                width = 250,
                height = 250

            )

            imageBitmap.value = bitmap
        }

        imageBitmap.value?.let {
            Image(it, "")
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun PreviewZone(
    confessionViewModel: ConfessionViewModel
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scope = rememberCoroutineScope()

        LaunchedEffect(true) {
            confessionViewModel.generateResponse()
        }

        val responseState = confessionViewModel.response.collectAsState()
        val confessionResponse = responseState.value


        val MoodText: @Composable() (String, MoodMeterElement) -> Unit = { title, element ->
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "- ${element.mood}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Text(
                    text = "- ${ResString.pleasantness}: ${element.pleasantness}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Text(
                    text = "- ${ResString.energy}: ${element.energy}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }

        }

        val QuestionResponse: @Composable (String, String) -> Unit = { question, answer ->
            Column {
                Text(
                    text = question,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(Modifier.size(20.dp))

                Text(
                    text = answer,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(8/10f),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                confessionResponse?.let {
                    Column(
                        modifier = Modifier.fillMaxWidth(1/3f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = ResString.appName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        val now = LocalDateTime.now()
                        val displayPattern = DateTimeFormatter.ofPattern(ResString.timeFormat)
                        Text(
                            text = displayPattern.format(now),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        val presentMood = it.presentMood

                        presentMood?.let {
                            MoodText(ResString.presentMood,it)
                        } ?: Text(
                            text = ResString.noPresentMood,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.size(20.dp))

                        PreviewMoodImage(confessionResponse, presentMood)

                        Spacer(Modifier.size(20.dp))

                        QuestionResponse(
                            understandingFormattedText(presentMood),
                            it.understandingAnswer
                        )

                        Spacer(Modifier.size(20.dp))

                        QuestionResponse(
                            ResString.questionExpressing,
                            it.expressingAnswer
                        )

                        Spacer(Modifier.size(20.dp))

                        QuestionResponse(
                            ResString.questionRegulating,
                            it.regulatingAnswer
                        )

                        Spacer(Modifier.size(20.dp))

                        val desiredMood = it.desiredMood

                        desiredMood?.let {
                            MoodText(ResString.desiredMood,it)
                        } ?: Text(
                            text = ResString.noDesiredMood,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.size(20.dp))

                        PreviewMoodImage(confessionResponse, desiredMood)

                        Spacer(Modifier.size(20.dp))

                        QuestionResponse(
                            howCanYouFeelFormattedText(desiredMood),
                            it.howCanYouFeelAnswer
                        )

                        Spacer(Modifier.size(20.dp))

                        QuestionResponse(
                            ResString.questionOneThingDoneWell,
                            it.doneWellAnswer
                        )

                        Spacer(Modifier.size(20.dp))

                        QuestionResponse(
                            ResString.questionOneThingToImproveOn,
                            it.toImproveAnswer
                        )

                        Spacer(Modifier.size(20.dp))

                        QuestionResponse(
                            ResString.questionComplacency,
                            it.complancencyAnswer
                        )

                        Spacer(Modifier.size(20.dp))

                        Text(
                            text = ResString.tasks,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.size(20.dp))

                        it.tasks.forEach {
                            Row(
                                modifier = Modifier.fillMaxWidth(6/10f),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "- ${it.name}",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )

                                Text(
                                    text = "${it.duration.inWholeMinutes} min.",
                                    maxLines = 1,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        }

                    }
                } ?: Text(ResString.problemGeneratingConfession)
            }
        }
    }
}