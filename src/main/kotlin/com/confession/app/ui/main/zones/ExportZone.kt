package com.confession.app.ui.main.zones

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import com.confession.app.ResString
import com.confession.app.export.pdf.ConfessionPdf
import com.confession.app.export.receipt.ConfessionReceipt
import com.confession.app.service.ConfessionViewModel
import com.confession.app.usb.ConfessionUsb

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExportZone(confessionViewModel: ConfessionViewModel) {

    LaunchedEffect(true) {
        confessionViewModel.generateResponse()
    }

    val responseState = confessionViewModel.response.collectAsState()
    val confessionResponse = responseState.value

    val openDialog = remember { mutableStateOf(false)}
    var isAskingToClose by remember { mutableStateOf(false) }

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
                    //show alert dialog
                    openDialog.value = true
                }
            )

            Spacer(Modifier.size(50.dp))

            ExportItem(
                title = ResString.exportPrint,
                onClick = {

                }
            )
        }


        if (openDialog.value) {
            Window(
                title = ResString.chooseAReceiptPrinter,
                onCloseRequest = { openDialog.value = false }
            ) {
                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = ResString.chooseAReceiptPrinter,
                            style = MaterialTheme.typography.h4
                        )
                    }


                    val list = remember { ConfessionUsb.getUSBDevices() }

                    list.forEachIndexed { index, confessionUsbResource ->
                        Text(
                            text = "${index}. ${confessionUsbResource.name}",
                            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Black).padding(2.dp).clickable {
                                val confessionReceipt = ConfessionReceipt(confessionUsbResource)
                                if (confessionResponse != null) {
                                    confessionReceipt.create(confessionResponse)
                                }
                            }
                        )
                    }

                    Spacer(Modifier.size(10.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = ResString.printerNoticeLinux,
                            style = MaterialTheme.typography.body1
                        )

                        Text(
                            text = ResString.printerNoticeWindows,
                            style = MaterialTheme.typography.body1
                        )

                        Spacer(Modifier.size(10.dp))

                        Button(
                            onClick = {
                                openDialog.value = false
                            },
                        ) {
                            Text(
                                text = ResString.Cancel,
                                style = MaterialTheme.typography.h4
                            )
                        }
                    }

                }
            }
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