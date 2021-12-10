package com.confession.app.ui.main.zones

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.confession.app.model.Task
import com.confession.app.service.AccomplishViewModel
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun AccomplishZone(accomplishViewModel: AccomplishViewModel) {
    val scope = rememberCoroutineScope()

    val tasksState = accomplishViewModel.tasksState.collectAsState()
    val tasks = tasksState.value


    val taskToCreateTextValue = remember { mutableStateOf(TextFieldValue()) }
    val taskToCreateDuration = remember { mutableStateOf(Duration.minutes(1)) }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Create
        Row {
            TextField(
                value = taskToCreateTextValue.value,
                onValueChange = {
                    taskToCreateTextValue.value = it
                }
            )

            var expanded = remember { mutableStateOf(false) }
            var selectedIndex = remember { mutableStateOf(0) }

            val estimates = listOf(1,5,15,30,45,60,90,120)

            Column(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                Button(
                    onClick = {
                        expanded.value = true
                    }
                ) {
                    if(estimates[selectedIndex.value] == 1) Text("${estimates[selectedIndex.value]} minute")
                    else Text("${estimates[selectedIndex.value]} minutes")

                }
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                ) {
                    estimates.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            expanded.value = false
                            selectedIndex.value = index
                            taskToCreateDuration.value = Duration.minutes(estimates[index])
                        }) {
                            if(estimates[index] == 1) Text("${estimates[index]} minute")
                            else Text("${estimates[index]} minutes")
                        }
                    }
                }
            }

            Button(
                onClick = {
                    scope.launch {
                        val newTask = Task(
                            name = taskToCreateTextValue.value.text,
                            duration = taskToCreateDuration.value
                        )
                        accomplishViewModel.addTask(newTask)
                    }
                }
            ) {
                Text("Create")
            }
        }
        //Total Information
        Row {
            val timeInHours =
                if(tasks.isNotEmpty())
                    tasks.map { it.duration }
                        .reduce { acc, duration -> acc.plus(duration) }
                        .toDouble(DurationUnit.HOURS)
                else 0

            val timeInHoursFormatted = "%.${4}f".format(timeInHours.toDouble())

            Text("Number of Tasks: ${tasks.size} $timeInHoursFormatted hours")
        }

        LazyColumn {
            items(tasks.size) { index ->
                Row {
                    val current = tasks[index]
                    //Read
                    Text("${current.name} ${current.duration.inWholeMinutes}")
                    //Update

                    //Modify
                    Box(
                        modifier = Modifier.clickable {
                            accomplishViewModel.reorder(current, -1)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, "")
                    }
                    Box(
                        modifier = Modifier.clickable {
                            accomplishViewModel.reorder(current, 1)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, "")
                    }

                    Box(
                        modifier = Modifier.clickable {
                            accomplishViewModel.removeTask(current)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, "")
                    }

                }
            }
        }



    }
}