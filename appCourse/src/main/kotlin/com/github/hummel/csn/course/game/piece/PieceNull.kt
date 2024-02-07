package com.github.hummel.csn.course.game.piece

import com.github.hummel.csn.course.game.Move
import com.github.hummel.csn.course.game.board.Board

class PieceNull : Piece(PieceTeams.NO_COLOR, PieceTypes.NULL) {
	override fun availableMoves(board: Board, currentCoord: Coordinate): List<Move> = emptyList()
} 