package hummel.msg

import hummel.game.piece.Coordinate
import java.io.Serializable

class MovementMessage : Serializable {
	var isPieceKilled: Boolean = false
	lateinit var destinationCoordinate: Coordinate
	lateinit var currentCoordinate: Coordinate
}