package hummel

import java.io.IOException
import java.net.ServerSocket
import kotlin.system.exitProcess

fun main() {
	Server()
}

class Server {
	private val controlPort = 1025
	private var welcomeSocket: ServerSocket? = null
	private var serverRunning = true

	init {
		try {
			welcomeSocket = ServerSocket(controlPort)
		} catch (e: IOException) {
			println("Could not create server socket")
			exitProcess(-1)
		}
		println("FTP Server started listening on port $controlPort")
		var noOfThreads = 0
		while (serverRunning) {
			try {
				val client = welcomeSocket!!.accept()
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
		try {
			welcomeSocket!!.close()
			println("Server was stopped")
		} catch (e: IOException) {
			println("Problem stopping server")
			exitProcess(-1)
		}
	}
}