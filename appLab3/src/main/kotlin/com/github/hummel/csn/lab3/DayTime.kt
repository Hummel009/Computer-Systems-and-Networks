package com.github.hummel.csn.lab3

import java.net.ServerSocket
import java.net.Socket
import java.util.*

fun launchDayTimeServer() {
	val serverPort = 13

	val serverSocket = ServerSocket(serverPort)
	println("Server is ON and using port $serverPort")

	val socket = serverSocket.accept()
	println("Connection from ${socket.inetAddress.hostAddress}")

	val outputStream = socket.getOutputStream()
	val date = "${Date()}\n"
	outputStream.write(date.toByteArray())

	socket.close()
}

fun launchDayTimeClient() {
	val serverAddress = "127.0.0.1"
	val serverPort = 13

	val socket = Socket(serverAddress, serverPort)
	val inputStream = socket.getInputStream()
	val buffer = ByteArray(4096)

	val length = inputStream.read(buffer)
	val result = String(buffer, 0, length)

	println("Current time: $result")
	socket.close()
}