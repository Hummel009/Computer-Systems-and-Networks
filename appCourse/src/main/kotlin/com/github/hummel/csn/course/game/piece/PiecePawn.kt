package com.github.hummel.csn.course.game.piece

import com.github.hummel.csn.course.game.Move
import com.github.hummel.csn.course.game.Pieces
import com.github.hummel.csn.course.game.board.Board
import com.github.hummel.csn.course.game.board.Tile
import com.github.hummel.csn.course.game.util.UtilBoard

class PiecePawn(team: PieceTeams) : Piece(team, PieceTypes.PAWN) {
	override fun availableMoves(board: Board, currentCoord: Coordinate): List<Move> {
		val possibleMoves: MutableList<Move> = ArrayList()
		val currentTile = board.getTile(currentCoord)
		var destinationTile: Tile
		for (coord in Pieces.PAWN_MOVES.getValue(team)["Normal"]!!) {
			if (!UtilBoard.isValidCoordinate(currentCoord.plus(coord))) {
				continue
			}
			destinationTile = board.getTile(currentCoord.plus(coord))
			if (!destinationTile.hasPiece()) {
				Move(board, currentTile, destinationTile).let { possibleMoves.add(it) }
			}
		}
		if (currentTile.coordinate.y == Pieces.getPawnStartPosY(team)) {
			for (coord in Pieces.PAWN_MOVES.getValue(team)["Start"]!!) {
				if (!UtilBoard.isValidCoordinate(currentCoord.plus(coord))) {
					continue
				}
				destinationTile = board.getTile(currentCoord.plus(coord))
				if (!destinationTile.hasPiece()) {
					Move(board, currentTile, destinationTile).let { possibleMoves.add(it) }
				}
			}
		}
		for (coord in Pieces.PAWN_MOVES.getValue(team)["Attack"]!!) {
			if (!UtilBoard.isValidCoordinate(currentCoord.plus(coord))) {
				continue
			}
			destinationTile = board.getTile(currentCoord.plus(coord))
			if (destinationTile.hasPiece() && (destinationTile.piece.team !== team)) {
				Move(board, currentTile, destinationTile).let { possibleMoves.add(it) }
			}
		}
		return possibleMoves
	}
}