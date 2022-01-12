package com.confession.app.export.receipt

import com.github.anastaciocintra.escpos.EscPos
import com.github.anastaciocintra.output.PrinterOutputStream

class ConfessionReceipt {

    fun create(confessionReceipt: ConfessionReceipt?) {
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
    ConfessionReceipt().create(null)
}