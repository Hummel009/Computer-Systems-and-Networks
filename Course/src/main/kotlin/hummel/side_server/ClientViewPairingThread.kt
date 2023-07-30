package hummel.side_server

import hummel.game.piece.PieceTeams
import hummel.msg.Message
import hummel.side_server.Server.Companion.sendMessage

// Целью этого потока является объединение каждого клиента с его соперником в игре.
// Из-за того, что игра в шахматы — это игра 1 на 1, операция сопряжения происходит только между двумя клиентами.
class ClientViewPairingThread(private var clientView: ClientView) : Thread() {
    override fun run() {
        while (this.clientView.socket.isConnected && this.clientView.isWaiting && !this.clientView.isPaired) {
            try {
                Server.pairingLockForTwoPair.acquire(1)
                var chosenPair: ClientView? = null
                while (this.clientView.socket.isConnected && chosenPair == null) {
                    for (clientView in Server.clientViews) {
                        if (clientView != this.clientView && !clientView.isPaired && clientView.isWaiting) {
                            chosenPair = clientView
                            this.clientView.pair = clientView
                            clientView.pair = this.clientView
                            this.clientView.isWaiting = false
                            clientView.isWaiting = false
                            clientView.isPaired = true
                            this.clientView.isPaired = true
                            val message = Message(Message.MessageTypes.PAIRING)
                            message.content = "Your match"
                            sendMessage(this.clientView, message)
                            sendMessage(chosenPair, message)
                            val clientStartMessage = Message(Message.MessageTypes.START)
                            clientStartMessage.content = PieceTeams.WHITE
                            val pairClientStartMessage = Message(Message.MessageTypes.START)
                            pairClientStartMessage.content = PieceTeams.BLACK
                            sendMessage(this.clientView, clientStartMessage)
                            sendMessage(chosenPair, pairClientStartMessage)
                            break
                        }
                    }
                    sleep(1000)
                }
                Server.pairingLockForTwoPair.release(1)
            } catch (ex: InterruptedException) {
                println("Pairing thread could not been acquired 1 permit. There is an error occurred there.")
            }
        }
    }
}