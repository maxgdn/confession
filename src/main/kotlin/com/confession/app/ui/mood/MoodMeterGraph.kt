package com.confession.app.ui.mood

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.confession.app.ResString
import com.confession.app.meter.MoodMeter
import com.confession.app.meter.MoodMeterElement
import com.confession.app.model.MoodQuadrant
import com.confession.app.util.gradientBackground
import kotlin.math.sqrt
import kotlin.properties.Delegates

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MoodMeterQuadrantElement(
    moodMeterElement: MoodMeterElement,
    selected: MutableState<MoodMeterElement?>,
    hovered: MutableState<MoodMeterElement?>,
) {
    val selectMood = {
        selected.value = null
        selected.value = moodMeterElement
    }

    Column(
        modifier = Modifier

            .pointerMoveFilter(
                onEnter = {
                    hovered.value = moodMeterElement
                    false
                },
                onExit = {
                    hovered.value = null
                    false
                }
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { selectMood()  },
                    onDoubleTap = { selectMood() },
                    onLongPress = { selectMood() },
                    onTap = { selectMood() }
                )
            }
            .border(5.dp, Color.White)
            .padding(20.dp)

        ,
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RectangleShape)
                .background(Color.Transparent)
        )
    }

}

//TODO onClick func, labels
@Composable
fun MoodMeterQuadrant(
    moods: List<MoodMeterElement>,
    selected: MutableState<MoodMeterElement?>,
    hovered: MutableState<MoodMeterElement?>,
    quadrant: MoodQuadrant
) {

    var colors: List<Color>
    var angle by Delegates.notNull<Float>()

    when(quadrant) {
        MoodQuadrant.RED -> {
            colors = listOf(
                Color(
                    red = 173,
                    green = 37,
                    blue = 51
                ),
                Color(
                    red = 223,
                    green = 119,
                    blue = 64
                ),
            )

            angle = 315f
        }
        MoodQuadrant.YELLOW -> {
            colors = listOf(
                Color(
                    red = 224,
                    green = 186,
                    blue = 77
                ),
                Color(
                    red = 243,
                    green = 238,
                    blue = 86
                ),
            )

            angle = 225f
        }
        MoodQuadrant.BLUE -> {
            colors = listOf(
                Color(
                    red = 38,
                    green = 39,
                    blue = 95
                ),
                Color(
                    red = 85,
                    green = 168,
                    blue = 172
                ),
            )
            angle = 45f
        }
        MoodQuadrant.GREEN -> {
            colors = listOf(
                Color(
                    red = 61,
                    green = 130,
                    blue = 73
                ),
                Color(
                    red = 139,
                    green = 187,
                    blue = 87
                ),
            )

            angle = 135f
        }
    }

    Column(
        modifier = Modifier
            .gradientBackground(
                colors = colors,
                angle = angle
            ),
    ) {
        val sublistSize = sqrt(moods.size.toDouble()).toInt()

        for(i in 1..sublistSize) {
            val row = moods.subList(sublistSize * (i - 1), sublistSize * i)
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                row.forEach {
                    MoodMeterQuadrantElement(
                        moodMeterElement = it,
                        selected = selected,
                        hovered = hovered
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoodMeterGraph(moodMeter: MoodMeter) {

    val selectedMoodMeterElement: MutableState<MoodMeterElement?> = remember { mutableStateOf(null) }
    val nearestMoodElements: MutableState<List<MoodMeterElement?>> = remember { mutableStateOf(emptyList()) }
    val hoveredMoodMeterElement: MutableState<MoodMeterElement?> = remember { mutableStateOf(null) }

    LaunchedEffect(selectedMoodMeterElement.value) {
        val selected = selectedMoodMeterElement.value
        if(selected != null) nearestMoodElements.value = moodMeter.getMoodsNearest(selected)
        else nearestMoodElements.value = emptyList()
    }

    val filterByQuadrant: (MoodQuadrant) -> List<MoodMeterElement> = { quadrant: MoodQuadrant ->
        moodMeter.meter.filter { it.mood.quadrant == quadrant }
    }

    val red = filterByQuadrant(MoodQuadrant.RED)
    val yellow = filterByQuadrant(MoodQuadrant.YELLOW)
    val blue = filterByQuadrant(MoodQuadrant.BLUE)
    val green = filterByQuadrant(MoodQuadrant.GREEN)

    val graphWidth = remember { mutableStateOf(0.dp) }
    val graphHeight = remember { mutableStateOf(0.dp) }

    val textWidth = remember { mutableStateOf(0.dp) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(end = textWidth.value),
            horizontalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier
                    .height(graphHeight.value)
                    .onGloballyPositioned { coordinates ->
                            textWidth.value = coordinates.size.toSize().width.dp
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier
                        .rotate(270f)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = ""
                    )
                    Text(
                        text = ResString.energy,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = ""
                    )
                }
            }

            BoxWithConstraints(
                modifier = Modifier.border(3.dp, Color.Black).onGloballyPositioned { coordinates ->
                    graphWidth.value = coordinates.size.toSize().width.dp
                    graphHeight.value = coordinates.size.toSize().height.dp
                }
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Row {
                        MoodMeterQuadrant(
                            moods = red,
                            selected = selectedMoodMeterElement,
                            hovered = hoveredMoodMeterElement,
                            quadrant = MoodQuadrant.RED
                        )
                        MoodMeterQuadrant(
                            moods = yellow,
                            selected = selectedMoodMeterElement,
                            hovered = hoveredMoodMeterElement,
                            quadrant = MoodQuadrant.YELLOW
                        )
                    }
                    Row {
                        MoodMeterQuadrant(
                            moods = blue,
                            selected = selectedMoodMeterElement,
                            hovered = hoveredMoodMeterElement,
                            quadrant = MoodQuadrant.BLUE
                        )
                        MoodMeterQuadrant(
                            moods = green,
                            selected = selectedMoodMeterElement,
                            hovered = hoveredMoodMeterElement,
                            quadrant = MoodQuadrant.GREEN
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.width(graphWidth.value).padding(top = 40.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = ""
            )
            Text(
                text = ResString.pleasantness,
                fontWeight = FontWeight.ExtraBold
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = ""
            )
        }

        Column {
            val hovered = hoveredMoodMeterElement.value
            Row(
                modifier = Modifier.requiredHeight(25.dp)
            ) {
                hovered?.let {
                    Text(
                        text = "Mood: ${it.mood} Pleasantness: ${it.pleasantness} Energy: ${it.energy}",
                    )
                }
            }

        }

        Column(
            modifier = Modifier.requiredWidth(graphWidth.value),
        ) {
            val selected = selectedMoodMeterElement.value
            val nearest = nearestMoodElements.value

            if(nearest.isNotEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NearestRow {
                        NearestElement(
                            modifier = Modifier.weight(1f, fill = true),
                            moodMeterElement = nearest[0],
                            selected = selected
                        )
                        NearestElement(
                            modifier = Modifier.weight(1f, fill = true),
                            moodMeterElement = nearest[1],
                            selected = selected
                        )
                        NearestElement(
                            modifier = Modifier.weight(1f, fill = true),
                            moodMeterElement = nearest[2],
                            selected = selected
                        )
                    }
                    NearestRow {
                        NearestElement(
                            modifier = Modifier.weight(1f, fill = true),
                            moodMeterElement = nearest[3],
                            selected = selected
                        )
                        NearestElement(
                            modifier = Modifier.weight(1f, fill = true),
                            moodMeterElement = nearest[4],
                            selected = selected
                        )
                        NearestElement(
                            modifier = Modifier.weight(1f, fill = true),
                            moodMeterElement = nearest[5],
                            selected = selected
                        )
                    }
                    NearestRow {
                        NearestElement(
                            modifier = Modifier.weight(1f, fill = true),
                            moodMeterElement = nearest[6],
                            selected = selected
                        )
                        NearestElement(
                            modifier = Modifier.weight(1f, fill = true),
                            moodMeterElement = nearest[7],
                            selected = selected
                        )
                        NearestElement(
                            modifier = Modifier.weight(1f, fill = true),
                            moodMeterElement = nearest[8],
                            selected = selected
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NearestRow(
    content: @Composable() () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center

    ) {
        content()
    }
}

@Composable
fun NearestElement(
    modifier: Modifier,
    moodMeterElement: MoodMeterElement?,
    selected: MoodMeterElement?,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if(moodMeterElement == null) Text(" ")
        else {
            Text(
                text = moodMeterElement.mood.name,
                fontWeight = if(selected == moodMeterElement) FontWeight.Bold else FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}

