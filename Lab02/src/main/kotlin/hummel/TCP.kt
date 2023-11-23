package hummel

import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.system.measureTimeMillis

fun launchClientTCP() {
	val serverAddress = InetAddress.getByName("192.168.56.1")
	val serverPort = 6061
	val tcpSocket = Socket(serverAddress, serverPort)

	val random = Random()
	val packetSize = 1024
	val data = Array(1024) { ByteArray(1024) }
	for (i in 0 until 1024) {
		random.nextBytes(data[i])
	}

	val time = measureTimeMillis {
		val outputStream = tcpSocket.getOutputStream()
		val inputStream = tcpSocket.getInputStream()

		for (i in 0 until 1024) {
			outputStream.write(data[i])
			outputStream.flush()

			val result = inputStream.read()
			if (result != 0) {
				println("Packet $i: error!")
			}
		}
	}

	val speed = packetSize * 1024 / (time / 1000.0)
	println("Speed: $speed byte/sec")
	tcpSocket.close()
}

fun launchServerTCP() {
	val serverPort = 6061
	val serverSocket = ServerSocket(serverPort)
	println("Server is ON and using port $serverPort")

	while (true) {
		val clientSocket = serverSocket.accept()
		println("Connection of the client: ${clientSocket.inetAddress.hostAddress}")

		val inputStream = clientSocket.getInputStream()
		val packetSize = 1024
		val expectedData = ByteArray(packetSize)
		val random = Random()

		repeat(1024) {
			val data = ByteArray(packetSize)
			inputStream.read(data)

			random.nextBytes(expectedData)
			val result = if (data.contentEquals(expectedData)) 1 else 0

			val outputStream = clientSocket.getOutputStream()
			outputStream.write(result)
			outputStream.flush()
		}

		clientSocket.close()
	}
}