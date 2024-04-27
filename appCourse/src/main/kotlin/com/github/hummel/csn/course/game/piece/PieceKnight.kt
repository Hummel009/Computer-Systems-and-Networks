package com.github.hummel.csn.course.game.piece

import com.github.hummel.csn.course.game.Move
import com.github.hummel.csn.course.game.Pieces
import com.github.hummel.csn.course.game.board.Board
import com.github.hummel.csn.course.game.board.Tile
import com.github.hummel.csn.course.game.util.UtilBoard

class PieceKnight(team: PieceTeams) : Piece(team, PieceTypes.KNIGHT) {
	override fun availableMoves(board: Board, currentCoord: Coordinate): List<Move> {
		val possibleMoves: MutableList<Move> = ArrayList()
		var destinationTile: Tile
		for (coord in Pieces.KNIGHT_MOVES) {
			if (!UtilBoard.isValidCoordinate(currentCoord.plus(coord))) {
				continue
			}
			destinationTile = board.getTile(currentCoord.plus(coord))
			if (!destinationTile.hasPiece()) {
				possibleMoves.add(Move(board.getTile(currentCoord), destinationTile))
			} else {
				if (destinationTile.piece.team !== team) {
					possibleMoves.add(Move(board.getTile(currentCoord), destinationTile))
				}
			}
		}
		return possibleMoves
	}
}