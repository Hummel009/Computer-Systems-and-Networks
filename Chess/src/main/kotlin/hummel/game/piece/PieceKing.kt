package hummel.game.piece

import hummel.game.Move
import hummel.game.Pieces
import hummel.game.board.Board
import hummel.game.board.Tile
import hummel.game.util.UtilBoard

class PieceKing(team: PieceTeams) : Piece(team, PieceTypes.KING) {
	override fun availableMoves(board: Board, currentCoord: Coordinate): List<Move> {
		val possibleMoves: MutableList<Move> = ArrayList()
		val currentTile = board.getTile(currentCoord)
		var destinationTile: Tile
		var destinationCoordinate: Coordinate?
		for (coord in Pieces.QUEEN_MOVES) {
			destinationCoordinate = currentCoord.plus(coord)
			if (!UtilBoard.isValidCoordinate(destinationCoordinate)) {
				continue
			}
			destinationTile = board.getTile(destinationCoordinate)
			if (!destinationTile.hasPiece()) {
				Move(board, currentTile, destinationTile).let { possibleMoves.add(it) }
			} else {
				if (destinationTile.piece.team !== team) {
					Move(board, currentTile, destinationTile).let { possibleMoves.add(it) }
				}
			}
		}
		return possibleMoves
	}
}