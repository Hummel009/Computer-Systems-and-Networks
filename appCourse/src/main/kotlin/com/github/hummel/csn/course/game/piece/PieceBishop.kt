package com.github.hummel.csn.course.game.piece

import com.github.hummel.csn.course.game.Move
import com.github.hummel.csn.course.game.Pieces
import com.github.hummel.csn.course.game.board.Board
import com.github.hummel.csn.course.game.board.Tile
import com.github.hummel.csn.course.game.util.UtilBoard

class PieceBishop(team: PieceTeams) : Piece(team, PieceTypes.BISHOP) {
	override fun availableMoves(board: Board, currentCoord: Coordinate): List<Move> {
		val possibleMoves: MutableList<Move> = ArrayList()
		val currentTile = board.getTile(currentCoord)
		var destinationTile: Tile
		var destinationCoordinate: Coordinate
		for (coord in Pieces.BISHOP_MOVES) {
			destinationCoordinate = currentCoord
			while (UtilBoard.isValidCoordinate(destinationCoordinate.plus(coord))) {
				destinationCoordinate = destinationCoordinate.plus(coord)
				destinationTile = board.getTile(destinationCoordinate)
				if (!destinationTile.hasPiece()) {
					Move(currentTile, destinationTile).let { possibleMoves.add(it) }
				} else {
					if (destinationTile.piece.team !== team) {
						Move(currentTile, destinationTile).let { possibleMoves.add(it) }
					}
					break
				}
			}
		}
		return possibleMoves
	}
}