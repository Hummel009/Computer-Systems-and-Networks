package com.github.hummel.csn.course.game

import com.github.hummel.csn.course.game.board.Tile
import com.github.hummel.csn.course.game.piece.Piece
import com.github.hummel.csn.course.game.piece.PieceNull
import java.io.Serializable

class Move(var currentTile: Tile, var destinationTile: Tile) : Serializable {
	var killedPiece: Piece = PieceNull()

	init {
		if (destinationTile.hasPiece()) {
			killedPiece = destinationTile.piece
		}
	}

	fun hasKilledPiece(): Boolean = killedPiece !is PieceNull
}