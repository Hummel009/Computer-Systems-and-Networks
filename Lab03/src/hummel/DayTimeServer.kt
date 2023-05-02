package hummel

import java.net.ServerSocket
import java.util.*

fun main() {
    val serverPort = 13

    val serverSocket = ServerSocket(serverPort)
    println("Server is running on port $serverPort")

    val socket = serverSocket.accept()
    println("Connection from ${socket.inetAddress.hostAddress}")

    val outputStream = socket.getOutputStream()
    val date = Date().toString() + "\n"
    outputStream.write(date.toByteArray())

    socket.close()
}