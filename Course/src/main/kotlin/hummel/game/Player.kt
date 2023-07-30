package hummel.game

import hummel.game.board.Board
import hummel.game.piece.PieceNull
import hummel.game.piece.PieceTeams
import java.io.Serializable

class Player(var team: PieceTeams) : Serializable {
	fun makeMove(board: Board, move: Move) {
		board.getTile(move.destinationTile.coordinate).piece = move.currentTile.piece
		board.getTile(move.currentTile.coordinate).piece = PieceNull()
	}
}