package com.confession.app.usb

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.PipedInputStream
import java.io.PipedOutputStream
import javax.usb.*

class CustomPolicy: UsbInterfacePolicy {
    override fun forceClaim(usbInterface: UsbInterface?): Boolean {
        return true
    }
}

class UsbJavaStream(
    device: UsbDevice
): PipedOutputStream() {
    init {
        val pipedInputStream = PipedInputStream()
        super.connect(pipedInputStream)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val configuration = device.usbConfigurations.first() as UsbConfiguration
                val usbInterface = configuration.usbInterfaces.first() as UsbInterface

                usbInterface.claim(CustomPolicy())

                val endpoint = usbInterface.usbEndpoints.first() as UsbEndpoint
                val endpointAddress = endpoint.usbEndpointDescriptor.bEndpointAddress()

                try {
                    val endpoint = usbInterface.getUsbEndpoint(endpointAddress)
                    val pipe = endpoint.usbPipe
                    pipe.open()
                    try {
                        val buf = ByteArray(1)
                        while (true) {
                            val n = pipedInputStream.read(buf)
                            if (n < 0) break
                            val sent = pipe.syncSubmit(buf)
                        }
                    } finally {
                        pipe.close()
                    }
                } finally {
                    usbInterface.release()
                }
            } catch (ex: Exception) {
                //throw RuntimeException(ex)
            }
        }

    }
}