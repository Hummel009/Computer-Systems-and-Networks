package hummel

import java.io.IOException
import java.net.ServerSocket

fun main() {
	Server()
}

class Server {
	private val controlPort = 1025
	private var welcomeSocket = ServerSocket(controlPort)
	private var serverRunning = true

	init {
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
}