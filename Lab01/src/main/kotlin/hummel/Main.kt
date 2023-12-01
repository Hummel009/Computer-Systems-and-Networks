package hummel

import java.net.NetworkInterface

fun main() {
	val interfaces = NetworkInterface.getNetworkInterfaces()
	while (interfaces.hasMoreElements()) {
		val networkInterface = interfaces.nextElement()
		val mac = networkInterface.hardwareAddress
		mac?.let { byteArr ->
			print("MAC Address of " + networkInterface.name + ": ")
			byteArr.indices.forEach {
				System.out.format("%02X%s", byteArr[it], if (it < byteArr.size - 1) "-" else "")
			}
			println()
		}
	}
}