package hummel.game.piece

import hummel.game.Move
import hummel.game.Pieces
import hummel.game.board.Board
import hummel.game.board.Tile
import hummel.game.util.UtilBoard

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
				possibleMoves.add(Move(board, board.getTile(currentCoord), destinationTile))
			} else {
				if (destinationTile.piece.team !== team) {
					possibleMoves.add(Move(board, board.getTile(currentCoord), destinationTile))
				}
			}
		}
		return possibleMoves
	}
}