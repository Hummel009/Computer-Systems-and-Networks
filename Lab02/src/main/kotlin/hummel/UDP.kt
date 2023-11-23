package hummel

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*
import kotlin.system.measureTimeMillis

fun udpSend() {
	val serverAddress = InetAddress.getByName("192.168.43.177")
	val serverPort = 9090
	val udpSocket = DatagramSocket()

	val random = Random()
	val packetSize = 1024
	val data = Array(1024) { ByteArray(1024) }
	for (i in 0 until 1024) {
		random.nextBytes(data[i])
	}

	val time = measureTimeMillis {
		for (i in 1 until 1024) {
			val packet = DatagramPacket(data[i], data[i].size, serverAddress, serverPort)
			udpSocket.send(packet)

			val buffer = ByteArray(1)
			val responsePacket = DatagramPacket(buffer, buffer.size)
			udpSocket.receive(responsePacket)
			val result = buffer[0].toInt()
			if (result != 0) {
				println("Packet $i: error!")
			}
		}
	}

	val speed = packetSize * 1024 / (time / 1000.0)
	println("Speed: $speed byte/sec")
	udpSocket.close()
}

fun udpReceive() {
	val serverPort = 9090
	val serverSocket = DatagramSocket(serverPort)
	println("Server is ON and using port $serverPort")

	val dataSize = 1024
	val expectedData = ByteArray(dataSize)
	val random = Random()
	random.nextBytes(expectedData)

	repeat(1024) {
		val data = ByteArray(dataSize)
		val packet = DatagramPacket(data, data.size)
		serverSocket.receive(packet)
		println("Connection of the client...")

		val result = if (data.contentEquals(expectedData)) 1 else 0

		val address = packet.address
		val port = packet.port
		val resultPacket = DatagramPacket(byteArrayOf(result.toByte()), 1, address, port)
		serverSocket.send(resultPacket)
	}
}