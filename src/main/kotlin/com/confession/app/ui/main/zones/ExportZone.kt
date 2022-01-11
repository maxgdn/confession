package com.confession.app.ui.main.zones

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.confession.app.ResString
import com.confession.app.export.pdf.ConfessionPdf
import com.confession.app.service.ConfessionViewModel

@Composable
fun ExportZone(confessionViewModel: ConfessionViewModel) {

    LaunchedEffect(true) {
        confessionViewModel.generateResponse()
    }

    val responseState = confessionViewModel.response.collectAsState()
    val confessionResponse = responseState.value

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
                    if (confessionResponse != null) {
                        ConfessionPdf().create(confessionResponse)
                    } else {
                        //alert
                    }
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