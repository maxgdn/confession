package com.confession.app.usb

import com.github.anastaciocintra.escpos.EscPos
import java.io.UnsupportedEncodingException
import javax.usb.*


class ConfessionUsb {

    data class ConfessionUsbResource(val name: String, val device: UsbDevice?)

    companion object {
        private fun dumpDevice(device: UsbDevice?) {
            // Dump information about the device itself
            println(device)
            val port = device!!.parentUsbPort
            if (port != null) {
                println("Connected to port: " + port.portNumber)
                println("Parent: " + port.usbHub)
            }

            // Dump device descriptor
            println(device.usbDeviceDescriptor)

            // Process all configurations
            for (configuration in device
                .usbConfigurations as List<UsbConfiguration?>) {
                // Dump configuration descriptor
                println(configuration!!.usbConfigurationDescriptor)

                // Process all interfaces
                for (iface in configuration
                    .usbInterfaces as List<UsbInterface?>) {
                    // Dump the interface descriptor
                    println(iface!!.usbInterfaceDescriptor)

                    // Process all endpoints
                    for (endpoint in iface
                        .usbEndpoints as List<UsbEndpoint?>) {
                        // Dump the endpoint descriptor
                        println(endpoint!!.usbEndpointDescriptor)
                    }
                }
            }

            // Dump child devices if device is a hub
            if (device.isUsbHub) {
                val hub = device as UsbHub?
                for (child in hub!!.attachedUsbDevices as List<UsbDevice?>) {
                    dumpDevice(child)
                }
            }
        }

        private fun padZeros(string: String): String {
            val maxLength = 4
            return if(string.length == maxLength) string
            else {
                val difference = maxLength - string.length
                var copy = string
                for(i in 1..difference) {
                    copy = "0$copy"
                }

                return copy
            }
        }

        /**
         * Dumps the name of the specified device to stdout.
         *
         * @param device
         * The USB device.
         * @throws UnsupportedEncodingException
         * When string descriptor could not be parsed.
         * @throws UsbException
         * When string descriptor could not be read.
         */
        @Throws(UnsupportedEncodingException::class, UsbException::class)
        private fun dumpName(device: UsbDevice?): String {
            // Read the string descriptor indices from the device descriptor.
            // If they are missing then ignore the device.
            val desc = device!!.usbDeviceDescriptor
            val iManufacturer = desc.iManufacturer()
            val iProduct = desc.iProduct()
            if (iManufacturer.toInt() == 0 || iProduct.toInt() == 0) return "Couldn't get device name"

            val idVendor = desc.idVendor()
            val idProduct = desc.idProduct()
            val vendorString = padZeros(Integer.toHexString(idVendor.toInt()))
            val productString = padZeros(Integer.toHexString(idProduct.toInt()))

            // Dump the device name
            return device.getString(iManufacturer) + " " + device.getString(iProduct) + " - ID " + vendorString + ":" + productString
        }

        /**
         * Processes the specified USB device.
         *
         * @param device
         * The USB device to process.
         */
        private fun processDevice(device: UsbDevice?, usbList: MutableList<ConfessionUsbResource>) {

            // When device is a hub then process all child devices
            if (device!!.isUsbHub) {
                val hub = device as UsbHub?
                for (child in hub!!.attachedUsbDevices as List<UsbDevice?>) {
                    processDevice(child, usbList)
                }
            } else {
                try {
                    val name = dumpName(device)
                    usbList.add(ConfessionUsbResource(name, device))
                } catch (e: Exception) {
                    // On Linux this can fail because user has no write permission
                    // on the USB device file. On Windows it can fail because
                    // no libusb device driver is installed for the device
                    //System.err.println("Ignoring problematic device: $e")
                    usbList.add(ConfessionUsbResource("${e.localizedMessage}", device))
                }
            }
        }

        private fun findDevice(hub: UsbHub?, vendorId: Short, productId: Short): UsbDevice? {
            for (device in hub!!.attachedUsbDevices as List<UsbDevice?>) {
                val desc = device!!.usbDeviceDescriptor
                if (desc.idVendor() == vendorId && desc.idProduct() == productId) return device
                if (device.isUsbHub) {
                    val foundDevice = findDevice(device as UsbHub?, vendorId, productId)
                    if (foundDevice != null) return foundDevice
                }
            }
            return null
        }

        fun getUSBDevices(): List<ConfessionUsbResource> {
            val services = UsbHostManager.getUsbServices();
//            System.out.println("USB Service Implementation: "
//                    + services.getImpDescription());
//            System.out.println("Implementation version: "
//                    + services.getImpVersion());
//            System.out.println("Service API version: " + services.getApiVersion());
//            System.out.println();

            val usbList = mutableListOf<ConfessionUsbResource>()
            processDevice(services.rootUsbHub, usbList)
            return usbList
        }
    }

}
