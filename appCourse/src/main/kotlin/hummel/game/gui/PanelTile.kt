package hummel.game.gui

import hummel.game.Data
import hummel.game.Move
import hummel.game.board.Board
import hummel.game.board.TileNull
import hummel.game.piece.Coordinate
import hummel.game.piece.PieceNull
import hummel.game.piece.PieceTeams
import hummel.game.piece.PieceTypes
import hummel.game.util.UtilBoard.getImageOfTeamPiece
import hummel.game.util.UtilMove.controlCheckState
import hummel.game.util.UtilMove.isValidMove
import hummel.msg.Message
import hummel.msg.MovementMessage
import hummel.side_client.Client
import java.awt.Color
import java.awt.Dimension
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel

class PanelTile(boardPanel: PanelBoard, var coordinate: Coordinate, chessBoard: Board, client: Client) : JPanel(
	GridBagLayout()
) {
	private var pieceIcon: JLabel = JLabel()

	init {
		this.add(pieceIcon)
		preferredSize = Dimension(Data.TILE_SIZE, Data.TILE_SIZE)
		assignTileColor(chessBoard)
		assignTilePieceIcon(chessBoard)
		addMouseListener(object : MouseListener {
			override fun mouseClicked(e: MouseEvent) {
				if (client.team !== chessBoard.currentPlayer.team) {
					return
				}
				if (!chessBoard.hasChosenTile()) {
					if (chessBoard.getTile(coordinate).hasPiece()) {
						if (chessBoard.currentPlayer.team !== chessBoard.getTile(coordinate).piece.team) {
							return
						}
					}
					chessBoard.getTile(coordinate).let { chessBoard.setChosenTile2(it) }
				} else {
					val destinationTile = chessBoard.getTile(coordinate)
					if (isValidMove(chessBoard, destinationTile)) {
						val move = Move(chessBoard, chessBoard.chosenTile, destinationTile)
						chessBoard.currentPlayer.makeMove(chessBoard, move)
						if (move.hasKilledPiece()) {
							client.game.bottomGameMenu.killedPiecesList.add(move.killedPiece.toString())
						}
						val msg = Message(Message.MessageTypes.MOVE)
						val movement = MovementMessage()
						movement.currentCoordinate = move.currentTile.coordinate
						movement.destinationCoordinate = move.destinationTile.coordinate
						if (move.killedPiece !is PieceNull) {
							movement.isPieceKilled = true
						}
						msg.content = movement
						client.sendToServer(msg)
						chessBoard.changeCurrentPlayer()
						client.game.bottomGameMenu.turnLBL.text = "Enemy Turn"
						client.game.bottomGameMenu.turnLBL.foreground = Color.RED
						if (move.hasKilledPiece()) {
							if (move.killedPiece.type === PieceTypes.KING) {
								val winnerTeam: PieceTeams =
									if (move.killedPiece.team === PieceTeams.BLACK) PieceTeams.WHITE else PieceTeams.BLACK
								JOptionPane.showMessageDialog(null, "Winner: $winnerTeam")
								val message = Message(Message.MessageTypes.END)
								message.content = null
								client.sendToServer(message)
							}
						}
					} else {
						if (destinationTile.hasPiece()) {
							if (chessBoard.currentPlayer.team !== chessBoard.getTile(coordinate).piece.team) {
								return
							}
						}
						chessBoard.setChosenTile2(destinationTile)
					}
					if (controlCheckState(chessBoard, PieceTeams.BLACK)) {
						JOptionPane.showMessageDialog(null, "Check state for team : " + PieceTeams.BLACK)
						val msg = Message(Message.MessageTypes.CHECK)
						msg.content = PieceTeams.BLACK
						client.sendToServer(msg)
					} else if (controlCheckState(chessBoard, PieceTeams.WHITE)) {
						JOptionPane.showMessageDialog(null, "Check state for team : " + PieceTeams.WHITE)
						val msg = Message(Message.MessageTypes.CHECK)
						msg.content = PieceTeams.WHITE
						client.sendToServer(msg)
					}
				}
				boardPanel.updateBoardGUI(chessBoard)
			}

			override fun mousePressed(e: MouseEvent) {}
			override fun mouseReleased(e: MouseEvent) {}
			override fun mouseEntered(e: MouseEvent) {}
			override fun mouseExited(e: MouseEvent) {}
		})
		validate()
	}

	fun assignTilePieceIcon(board: Board) {
		val thisTile = board.getTile(coordinate)
		if (thisTile is TileNull) {
			println("Tile is null")
			return
		}
		if (thisTile.hasPiece()) {
			pieceIcon.icon = getImageOfTeamPiece(thisTile.piece.team, thisTile.piece.type)
			pieceIcon.validate()
		} else if (!thisTile.hasPiece()) {
			pieceIcon.icon = null
			pieceIcon.validate()
		}
	}

	fun assignTileColor(board: Board) {
		if (coordinate.x % 2 == 0 && coordinate.y % 2 == 0 || coordinate.x % 2 == 1 && coordinate.y % 2 == 1) {
			background = Data.cellDark
		} else if (coordinate.x % 2 == 0 && coordinate.y % 2 == 1 || coordinate.x % 2 == 1 && coordinate.y % 2 == 0) {
			background = Data.cellLight
		}
		if (board.hasChosenTile()) {
			if (coordinate == board.chosenTile.coordinate) {
				background = Data.cellHover
			}
		}
	}
}