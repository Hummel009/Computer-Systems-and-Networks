package com.github.hummel.csn.course.game.board

import com.github.hummel.csn.course.game.piece.Coordinate
import com.github.hummel.csn.course.game.piece.Piece
import com.github.hummel.csn.course.game.piece.PieceNull
import java.io.Serializable

open class Tile(var coordinate: Coordinate, var piece: Piece) : Serializable {
	fun hasPiece(): Boolean = piece !is PieceNull

	override fun toString(): String = "$coordinate" + " Piece " + if (hasPiece()) "$piece" else "Empty"
}