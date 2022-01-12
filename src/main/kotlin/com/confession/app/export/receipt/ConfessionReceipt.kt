package com.confession.app.export.receipt

import com.confession.app.usb.ConfessionUsb
import com.github.anastaciocintra.escpos.EscPos
import com.github.anastaciocintra.output.PrinterOutputStream
import java.io.UnsupportedEncodingException
import javax.usb.*


class ConfessionReceipt(val confessionUsbResource: ConfessionUsb.ConfessionUsbResource) {

    fun create(confessionReceipt: ConfessionReceipt?) {
        confessionUsbResource.device

        PrinterOutputStream.getListPrintServicesNames().forEach {
            println(it)
        }
        val printService = PrinterOutputStream.getDefaultPrintService()
        val printerOutputStream = PrinterOutputStream(printService)
        val escpos = EscPos(printerOutputStream)

        escpos.writeLF("Hello Wold");
        escpos.feed(5);
        escpos.cut(EscPos.CutMode.FULL);
        escpos.close();

    }
}

fun main() {
    //val r = ConfessionReceipt()

}