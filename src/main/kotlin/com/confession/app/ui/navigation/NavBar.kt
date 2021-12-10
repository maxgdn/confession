package com.confession.app.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.confession.app.ui.zones.Zone
import com.confession.app.ui.zones.ZonesScope

@Composable
fun NavBar(zonesScope: ZonesScope) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                zonesScope.previous()
            }
        ) {
            Text("Prev")
        }

        Spacer(
            Modifier.width(20.dp)
        )

        var expanded = remember { mutableStateOf(false) }
        val items = Zone.values()
        var selectedIndex = zonesScope.index

        Column(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
            Button(
                onClick = {
                    expanded.value = true
                }
            ) {
                Text("${selectedIndex + 1}. ${items[selectedIndex].name}")
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        zonesScope.goTo(items[index])
                    }) {
                        Text(text = "${index + 1}. " + s)
                    }
                }
            }
        }

        Spacer(
            Modifier.width(20.dp)
        )

        Button(
            onClick = {
                zonesScope.next()
            }
        ) {
            Text("Next")

        }
    }
}