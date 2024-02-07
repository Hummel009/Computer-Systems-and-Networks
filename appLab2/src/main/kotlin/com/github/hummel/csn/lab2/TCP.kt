package com.github.hummel.csn.lab2

import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.security.SecureRandom
import kotlin.system.measureTimeMillis

fun launchClientTCP() {
	val serverAddress = InetAddress.getByName("127.0.0.1")
	val serverPort = 6061
	val tcpSocket = Socket(serverAddress, serverPort)

	val random = SecureRandom()
	val packetSize = 1024
	val data = Array(1024) { ByteArray(1024) }
	data.forEach { random.nextBytes(it) }

	val time = measureTimeMillis {
		val outputStream = tcpSocket.getOutputStream()
		val inputStream = tcpSocket.getInputStream()

		data.forEachIndexed { i, item ->
			outputStream.write(item)
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
		val random = SecureRandom()

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