package hummel.side_client

import hummel.game.Move
import hummel.game.piece.PieceTeams
import hummel.game.piece.PieceTypes
import hummel.msg.Message
import hummel.msg.Message.MessageTypes
import hummel.msg.MovementMessage
import java.awt.Color
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger
import javax.swing.JOptionPane

// Целью этого потока является постоянное прослушивание сервера, чтобы отследить, что во входной поток поступает сообщение.
// Если есть сообщение, то решаем, что произойдет.
class ClientListenThread(private var client: Client) : Thread() {
	override fun run() {
		while (!client.socket.isClosed) {
			try {
				val msg = client.fromServer.readObject() as Message
				when (msg.type) {
					MessageTypes.START -> {
						val serverChosenTeam = msg.content as PieceTeams?
						client.team = (serverChosenTeam ?: return)
					}

					MessageTypes.PAIRING -> {
						client.isPaired = true
						client.game.mainMenu.playBTN.isEnabled = true
						client.game.mainMenu.infoLBL.text = "Matched. Click To Start Game"
					}

					MessageTypes.MOVE -> {
						val movement = msg.content as MovementMessage?
						val board = client.game.chessBoard
						val player = board.currentPlayer
						val move = Move(
							board, board.getTile((movement ?: return).currentCoordinate),
							board.getTile(
								movement.destinationCoordinate
							)
						)
						player.makeMove(board, move)
						client.game.boardPanel.updateBoardGUI(client.game.chessBoard)
						if (move.hasKilledPiece() && (move.killedPiece.type === PieceTypes.KING)) {
							val winnerTeam: PieceTeams =
								if (move.killedPiece.team === PieceTeams.BLACK) PieceTeams.WHITE else PieceTeams.BLACK
							JOptionPane.showMessageDialog(null, "Winner: $winnerTeam")
							val message = Message(MessageTypes.END)
							message.content = null
							client.sendToServer(message)
							break
						}
						board.changeCurrentPlayer()
						client.game.bottomGameMenu.turnLBL.text = "Your Turn"
						client.game.bottomGameMenu.turnLBL.foreground = Color.BLACK
					}

					MessageTypes.CHECK -> {
						val checkStateTeam = msg.content as PieceTeams?
						JOptionPane.showMessageDialog(null, "Check state for team: " + checkStateTeam.toString())
					}

					MessageTypes.LEAVE -> {
						JOptionPane.showMessageDialog(null, "Enemy left. Returning to the Menu.")
						client.game.gameFrame.remove(client.game.boardPanel)
						client.game.createMainMenu()
					}

					else -> {}
				}
			} catch (ex: IOException) {
				Logger.getLogger(ClientListenThread::class.java.name).log(Level.SEVERE, null, ex)
			} catch (ex: ClassNotFoundException) {
				println("Class Not Found")
			}
		}
	}
}