package hummel.game.board

import hummel.game.piece.Coordinate
import hummel.game.piece.Piece
import hummel.game.piece.PieceNull
import java.io.Serializable

open class Tile(var coordinate: Coordinate, var piece: Piece) : Serializable {
	fun hasPiece(): Boolean {
		return piece !is PieceNull
	}

	override fun toString(): String {
		return coordinate.toString() + " Piece " + if (hasPiece()) piece.toString() else "Empty"
	}
}