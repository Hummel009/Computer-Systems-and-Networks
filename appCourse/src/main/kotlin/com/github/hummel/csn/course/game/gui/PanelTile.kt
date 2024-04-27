package com.github.hummel.csn.course.game.gui

import com.github.hummel.csn.course.game.Data
import com.github.hummel.csn.course.game.Move
import com.github.hummel.csn.course.game.board.Board
import com.github.hummel.csn.course.game.board.TileNull
import com.github.hummel.csn.course.game.piece.Coordinate
import com.github.hummel.csn.course.game.piece.PieceNull
import com.github.hummel.csn.course.game.piece.PieceTeams
import com.github.hummel.csn.course.game.piece.PieceTypes
import com.github.hummel.csn.course.game.util.UtilBoard.getImageOfTeamPiece
import com.github.hummel.csn.course.game.util.UtilMove.controlCheckState
import com.github.hummel.csn.course.game.util.UtilMove.isValidMove
import com.github.hummel.csn.course.msg.Message
import com.github.hummel.csn.course.msg.MovementMessage
import com.github.hummel.csn.course.side_client.Client
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
					val hasPiece = chessBoard.getTile(coordinate).hasPiece()
					val notOfThisTeam = chessBoard.currentPlayer.team !== chessBoard.getTile(coordinate).piece.team
					if (hasPiece && notOfThisTeam) {
						return
					}
					chessBoard.getTile(coordinate).let { chessBoard.setChosenTile2(it) }
				} else {
					val destinationTile = chessBoard.getTile(coordinate)
					if (isValidMove(chessBoard, destinationTile)) {
						val move = Move(chessBoard.chosenTile, destinationTile)
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
						if (move.hasKilledPiece() && move.killedPiece.type === PieceTypes.KING) {
							val winnerTeam: PieceTeams =
								if (move.killedPiece.team === PieceTeams.BLACK) PieceTeams.WHITE else PieceTeams.BLACK
							JOptionPane.showMessageDialog(null, "Winner: $winnerTeam")
							val message = Message(Message.MessageTypes.END)
							message.content = null
							client.sendToServer(message)
						}
					} else {
						val hasPiece = destinationTile.hasPiece()
						val notOfThisTeam = chessBoard.currentPlayer.team !== chessBoard.getTile(coordinate).piece.team
						if (hasPiece && notOfThisTeam) {
							return
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
		if (board.hasChosenTile() && (coordinate == board.chosenTile.coordinate)) {
			background = Data.cellHover
		}
	}
}