package hummel

import java.net.NetworkInterface
import java.net.SocketException

fun main() {
    try {
        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val networkInterface = interfaces.nextElement()
            val mac = networkInterface.hardwareAddress
            if (mac != null) {
                print("MAC Address of " + networkInterface.name + ": ")
                for (i in mac.indices) {
                    System.out.format("%02X%s", mac[i], if (i < mac.size - 1) "-" else "")
                }
                println()
            }
        }
    } catch (e: SocketException) {
        e.printStackTrace()
    }
}