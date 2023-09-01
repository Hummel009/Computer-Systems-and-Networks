package hummel.game.piece

import hummel.game.Move
import hummel.game.board.Board
import java.io.Serializable

abstract class Piece(val team: PieceTeams, val type: PieceTypes) : Serializable {
	override fun toString(): String = "$team $type"

	abstract fun availableMoves(board: Board, currentCoord: Coordinate): List<Move>
}