package hummel

import java.net.*

fun main() {
	val serverAddress = "192.168.43.246"
	val serverPort = 13

	val socket = Socket(serverAddress, serverPort)
	val inputStream = socket.getInputStream()
	val buffer = ByteArray(4096)

	val length = inputStream.read(buffer)
	val result = String(buffer, 0, length)

	println("Current time: $result")
	socket.close()
}