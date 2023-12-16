package hummel.side_server

import java.io.IOException

// этот поток вызывается сервером напрямую, когда сервер открывается и никогда не закрывается.
// Целью этого потока всегда является ожидание новых запросов на подключение клиента, чтобы принять или отклонить их.
class ClientViewRequestThread(private var server: Server) : Thread() {
	override fun run() {
		while (!server.socket.isClosed) {
			try {
				val socket = server.socket.accept()
				println("Client connecting: ${socket.inetAddress.hostAddress}")
				val clientView = ClientView(socket)
				clientView.listen()
				Server.clientViews.add(clientView)
			} catch (ex: IOException) {
				println("There is an error occurred when the new client being accepted.")
			}
		}
	}
}