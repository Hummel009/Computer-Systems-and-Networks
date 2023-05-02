package hummel.side_server

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

// Этот класс содержит всю информацию о клиенте и представляет клиент на стороне сервера.
// Если бы этого класса не было, клиент на стороне сервера выглядел бы просто как сокет.
// Но клиент — это больше, чем сокет.
class ClientView(var socket: Socket) {
	private var listenThread = ClientViewListenThread(this)
	var pairingThread = ClientViewPairingThread(this)
	var fromClient = ObjectInputStream(socket.getInputStream())
	var toClient = ObjectOutputStream(socket.getOutputStream())
	var pair: ClientView? = null
	var isPaired = false
	var isWaiting = false

	fun sendToClient(msg: Any?) {
		toClient.writeObject(msg)
	}

	fun listen() {
		listenThread.start()
	}
}