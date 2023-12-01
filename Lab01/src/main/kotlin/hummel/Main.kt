package hummel

import java.net.NetworkInterface

fun main() {
	val interfaces = NetworkInterface.getNetworkInterfaces()
	while (interfaces.hasMoreElements()) {
		val networkInterface = interfaces.nextElement()
		val mac = networkInterface.hardwareAddress
		mac?.let {
			print("MAC Address of " + networkInterface.name + ": ")
			for (i in it.indices) {
				System.out.format("%02X%s", it[i], if (i < it.size - 1) "-" else "")
			}
			println()
		}
	}
}