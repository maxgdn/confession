package com.confession.app.export.receipt

import com.confession.app.model.ConfessionResponse
import com.confession.app.usb.ConfessionUsb
import com.confession.app.usb.UsbJavaStream
import com.github.anastaciocintra.escpos.EscPos

class ConfessionReceipt(private val confessionUsbResource: ConfessionUsb.ConfessionUsbResource) {

    fun create(confessionResponse: ConfessionResponse) {
        val device = confessionUsbResource.device
        device?.let {
            val stream = UsbJavaStream(device)

            var a = ""

            for(i in 1..32) {
                a += "a"
            }
            println(a.length)


            val beep = byteArrayOf(0x1b, 0x42, 0x02, 0x01)

            val test = byteArrayOf(0x1d, 0x28, 0x41)
            val escPos = EscPos(stream)
            escPos.writeLF("Hello Usb4Java")
                .writeLF("Another Line")
                .feed(10)
                .writeLF(a)
                .writeLF("Test")
                .write(test, 0, test.size)
                .writeLF("Number 2")
                .write(beep, 0, beep.size)
                .cut(EscPos.CutMode.FULL)

        }
    }
}