package com.github.hummel.csn.course.game

import com.github.hummel.csn.course.game.board.Board
import com.github.hummel.csn.course.game.piece.PieceNull
import com.github.hummel.csn.course.game.piece.PieceTeams
import java.io.Serializable

class Player(var team: PieceTeams) : Serializable {
	fun makeMove(board: Board, move: Move) {
		board.getTile(move.destinationTile.coordinate).piece = move.currentTile.piece
		board.getTile(move.currentTile.coordinate).piece = PieceNull()
	}
}