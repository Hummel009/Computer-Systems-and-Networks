package com.github.hummel.csn.course.game.util

import com.github.hummel.csn.course.game.Pieces
import com.github.hummel.csn.course.game.board.Board
import com.github.hummel.csn.course.game.board.Tile
import com.github.hummel.csn.course.game.piece.Coordinate
import com.github.hummel.csn.course.game.piece.PieceTeams
import com.github.hummel.csn.course.game.piece.PieceTypes
import com.github.hummel.csn.course.game.util.UtilBoard.isValidCoordinate

object UtilMove {
	fun isValidMove(board: Board, destinationTile: Tile): Boolean {
		if (!board.hasChosenTile()) {
			return false
		}
		for (m in board.chosenTile.piece.availableMoves(board, board.chosenTile.coordinate)) {
			if (m.destinationTile.coordinate == destinationTile.coordinate) {
				return true
			}
		}
		return false
	}

	fun controlCheckState(board: Board, team: PieceTeams): Boolean {
		var destinationTile: Tile
		val currentCoord = board.getCoordOfGivenTeamPiece(team, PieceTypes.KING)
		for (coord in Pieces.KNIGHT_MOVES) {
			if (currentCoord != null) {
				if (!isValidCoordinate(currentCoord.plus(coord))) {
					continue
				}
				destinationTile = board.getTile(currentCoord.plus(coord))
				val hasPiece = destinationTile.hasPiece()
				val notOfThisTeam = destinationTile.piece.team !== team
				val isKnight = destinationTile.piece.type === PieceTypes.KNIGHT
				return hasPiece && (notOfThisTeam && isKnight)
			}
		}
		if (currentCoord != null) {
			board.getTile(currentCoord)
			var destinationCoordinate: Coordinate
			for (coord in Pieces.ROOK_MOVES) {
				destinationCoordinate = currentCoord
				while (isValidCoordinate(destinationCoordinate.plus(coord))) {
					destinationCoordinate = destinationCoordinate.plus(coord)
					destinationTile = board.getTile(destinationCoordinate)
					if (destinationTile.hasPiece()) {
						if (destinationTile.piece.team === team) {
							break
						}
						return if (destinationTile.piece.team !== team && (destinationTile.piece.type === PieceTypes.ROOK || destinationTile.piece.type === PieceTypes.QUEEN)) {
							true
						} else {
							break
						}
					}
				}
			}
			for (coord in Pieces.BISHOP_MOVES) {
				destinationCoordinate = currentCoord
				while (isValidCoordinate(destinationCoordinate.plus(coord))) {
					destinationCoordinate = destinationCoordinate.plus(coord)
					destinationTile = board.getTile(destinationCoordinate)
					if (destinationTile.hasPiece()) {
						if (destinationTile.piece.team === team) {
							break
						}
						return if (destinationTile.piece.team !== team && (destinationTile.piece.type === PieceTypes.BISHOP || destinationTile.piece.type === PieceTypes.QUEEN)) {
							true
						} else {
							break
						}
					}
				}
			}
			for (coord in Pieces.PAWN_MOVES[team]!!["Attack"]!!) {
				if (!isValidCoordinate(currentCoord.plus(coord))) {
					continue
				}
				destinationTile = board.getTile(currentCoord.plus(coord))
				val isPawn = destinationTile.piece.type === PieceTypes.PAWN
				val notOfThisTeam = destinationTile.piece.team !== team
				val hasPiece = destinationTile.hasPiece()
				return hasPiece && (notOfThisTeam && isPawn)
			}
		}
		return false
	}
}