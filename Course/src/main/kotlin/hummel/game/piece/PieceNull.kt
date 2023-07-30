package hummel.game.piece

import hummel.game.Move
import hummel.game.board.Board

class PieceNull : Piece(PieceTeams.NO_COLOR, PieceTypes.NULL) {
	override fun availableMoves(board: Board, currentCoord: Coordinate): List<Move> {
		return emptyList()
	}
} 