package hummel

import java.net.NetworkInterface

fun main() {
	val interfaces = NetworkInterface.getNetworkInterfaces()
	while (interfaces.hasMoreElements()) {
		val networkInterface = interfaces.nextElement()
		val mac = networkInterface.hardwareAddress
		mac?.let {
			print("MAC Address of " + networkInterface.name + ": ")
			it.forEachIndexed { i, item ->
				System.out.format("%02X%s", item, if (i < it.size - 1) "-" else "")
			}
			println()
		}
	}
}