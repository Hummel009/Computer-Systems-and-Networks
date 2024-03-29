package com.github.hummel.csn.lab4

import java.io.IOException
import java.net.ServerSocket

private const val controlPort: Int = 1025
private var welcomeSocket: ServerSocket = ServerSocket(controlPort)
private var serverRunning: Boolean = true

fun main() {
	println("FTP Server started listening on port $controlPort")
	var noOfThreads = 0
	while (serverRunning) {
		try {
			val client = welcomeSocket.accept()
			val dataPort = controlPort + noOfThreads + 1

			val w = Worker(client, dataPort)
			println("New connection received. Worker was created.")
			noOfThreads++
			w.start()
		} catch (e: IOException) {
			println("Exception encountered on accept")
			e.printStackTrace()
		}
	}
	welcomeSocket.close()
}