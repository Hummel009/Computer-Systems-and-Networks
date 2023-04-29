package hummel.game

import hummel.game.board.Board
import hummel.game.board.Tile
import hummel.game.piece.Piece
import hummel.game.piece.PieceNull
import java.io.Serializable

class Move(var board: Board, var currentTile: Tile, var destinationTile: Tile) : Serializable {
	var killedPiece: Piece = PieceNull()

	init {
		if (destinationTile.hasPiece()) {
			killedPiece = destinationTile.piece
		}
	}

	fun hasKilledPiece(): Boolean {
		return killedPiece !is PieceNull
	}
}