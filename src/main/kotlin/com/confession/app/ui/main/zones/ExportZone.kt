package com.confession.app.ui.main.zones

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.confession.app.ResString

@Composable
fun ExportZone() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(

        ) {
            ExportItem(
                title = ResString.exportPDF,
                onClick = {

                }
            )

            Spacer(Modifier.size(50.dp))

            ExportItem(
                title = ResString.exportReceipt,
                onClick = {

                }
            )

            Spacer(Modifier.size(50.dp))

            ExportItem(
                title = ResString.exportPrint,
                onClick = {

                }
            )
        }
    }
}

@Composable
fun ExportItem(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = title,
            fontSize = 28.sp
        )
    }
}