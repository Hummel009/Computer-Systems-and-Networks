package hummel.side_server

import hummel.msg.Message
import java.net.ServerSocket
import java.util.concurrent.Semaphore

class Server(port: Int) {
	var socket = ServerSocket(port)
	private var listenConnectionRequestThread = ClientViewRequestThread(this)

	fun listenClientConnectionRequests() {
		listenConnectionRequestThread.start()
	}

	companion object {
		var clientViews = ArrayList<ClientView>()
		var pairingLockForTwoPair = Semaphore(1, true)

		fun sendMessage(client: ClientView, message: Message?) {
			client.toClient.writeObject(message)
		}
	}
}