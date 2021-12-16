package com.confession.app.ui.main.zones

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.confession.app.meter.MoodMeterGraphics
import com.confession.app.service.*
import com.confession.app.ui.misc.ResetButton
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun PreviewZone(
    confessionViewModel: ConfessionViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scope = rememberCoroutineScope()

        LaunchedEffect(true) {
            confessionViewModel.generateResponse()
        }

        val responseState = confessionViewModel.response.collectAsState()
        val confessionResponse = responseState.value

        println(confessionResponse)

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
                        modifier = Modifier.fillMaxWidth(1/3f).background(color = Color.Black),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BoxWithConstraints {
                            val imageBitmap: MutableState<ImageBitmap?> = remember { mutableStateOf(null) }

                            LaunchedEffect(confessionResponse) {
                                val buffer = MoodMeterGraphics.getMoodElementAsImage(
                                    confessionResponse.presentMood,
                                    width = 720,
                                    height = 720

                                )
                                imageBitmap.value = buffer
                            }

                            imageBitmap.value?.let {
                                Image(it, "")
                            }
                        }
                    }
                } ?: Text("A problem occurred in generating the confession.")
            }

        }
    }
}