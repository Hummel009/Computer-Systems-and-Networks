package hummel.side_server

import hummel.msg.Message
import hummel.msg.Message.MessageTypes

// Целью этого потока является прослушивание данных, поступающих во входной поток клиента.
// После поступления данных этот поток определяет, что он будет делать с этими данными, а затем возобновляет прослушивание входного потока.
// Это прослушивание никогда не заканчивается, пока соединение с клиентом не будет потеряно.
class ClientViewListenThread(private var clientView: ClientView) : Thread() {
	override fun run() {
		while (!clientView.socket.isClosed) {
			try {
				val msg = clientView.fromClient.readObject() as Message
				when (msg.type) {
					MessageTypes.PAIRING -> {
						clientView.isWaiting = true
						clientView.pairingThread.start()
					}

					MessageTypes.MOVE, MessageTypes.CHECK -> (clientView.pair ?: return).sendToClient(msg)
					MessageTypes.END, MessageTypes.LEAVE -> {
						clientView.isPaired = false
						clientView.isWaiting = false
						(clientView.pair ?: return).isWaiting = false
						(clientView.pair ?: return).isPaired = false
						(clientView.pair ?: return).pair = null
						clientView.pair = null
					}

					else -> {}
				}
			} catch (ignored: Exception) {
			}
		}
	}
}