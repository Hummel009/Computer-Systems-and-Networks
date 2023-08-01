package hummel.side_client

import hummel.game.gui.ClientGUI
import hummel.game.piece.PieceTeams
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class Client(var game: ClientGUI, private var serverIP: String, private var serverPort: Int) {
	var isPaired: Boolean = false
	var team: PieceTeams = PieceTeams.NO_COLOR
	lateinit var socket: Socket
	lateinit var fromServer: ObjectInputStream
	private lateinit var toServer: ObjectOutputStream
	private lateinit var listenThread: ClientListenThread

	fun init() {
		try {
			println("Connecting to the server $serverIP:$serverPort")
			socket = Socket(serverIP, serverPort)
			toServer = ObjectOutputStream(socket.getOutputStream())
			fromServer = ObjectInputStream(socket.getInputStream())
			listenThread = ClientListenThread(this)
			listenThread.start()
		} catch (ex: IOException) {
			println("Cannot connected to the server.")
		}
	}

	fun sendToServer(message: Any?) {
		toServer.writeObject(message)
	}
}