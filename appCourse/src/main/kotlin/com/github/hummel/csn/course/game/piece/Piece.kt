package com.github.hummel.csn.course.game.piece

import com.github.hummel.csn.course.game.Move
import com.github.hummel.csn.course.game.board.Board
import java.io.Serializable

abstract class Piece(val team: PieceTeams, val type: PieceTypes) : Serializable {
	override fun toString(): String = "$team $type"

	abstract fun availableMoves(board: Board, currentCoord: Coordinate): List<Move>
}