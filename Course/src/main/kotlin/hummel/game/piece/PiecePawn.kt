package hummel.game.piece

import hummel.game.Move
import hummel.game.Pieces
import hummel.game.board.Board
import hummel.game.board.Tile
import hummel.game.util.UtilBoard

class PiecePawn(team: PieceTeams) : Piece(team, PieceTypes.PAWN) {
	override fun availableMoves(board: Board, currentCoord: Coordinate): List<Move> {
		val possibleMoves: MutableList<Move> = ArrayList()
		val currentTile = board.getTile(currentCoord)
		var destinationTile: Tile
		for (coord in Pieces.PAWN_MOVES[team]!!["Normal"]!!) {
			if (!UtilBoard.isValidCoordinate(currentCoord.plus(coord))) {
				continue
			}
			destinationTile = board.getTile(currentCoord.plus(coord))
			if (!destinationTile.hasPiece()) {
				Move(board, currentTile, destinationTile).let { possibleMoves.add(it) }
			}
		}
		if (currentTile.coordinate.y == Pieces.getPawnStartPosY(team)) {
			for (coord in Pieces.PAWN_MOVES[team]!!["Start"]!!) {
				if (!UtilBoard.isValidCoordinate(currentCoord.plus(coord))) {
					continue
				}
				destinationTile = board.getTile(currentCoord.plus(coord))
				if (!destinationTile.hasPiece()) {
					Move(board, currentTile, destinationTile).let { possibleMoves.add(it) }
				}
			}
		}
		for (coord in Pieces.PAWN_MOVES[team]!!["Attack"]!!) {
			if (!UtilBoard.isValidCoordinate(currentCoord.plus(coord))) {
				continue
			}
			destinationTile = board.getTile(currentCoord.plus(coord))
			if (destinationTile.hasPiece()) {
				if (destinationTile.piece.team !== team) {
					Move(board, currentTile, destinationTile).let { possibleMoves.add(it) }
				}
			}
		}
		return possibleMoves
	}
}