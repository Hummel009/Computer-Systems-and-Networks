package hummel.game

import hummel.game.piece.Coordinate
import hummel.game.piece.PieceTeams
import java.util.*

object Pieces {
	var KNIGHT_MOVES: Array<Coordinate> = arrayOf(
		Coordinate(2, 1),
		Coordinate(-2, 1),
		Coordinate(2, -1),
		Coordinate(-2, -1),
		Coordinate(1, 2),
		Coordinate(-1, 2),
		Coordinate(1, -2),
		Coordinate(-1, -2)
	)
	var BISHOP_MOVES: Array<Coordinate> =
		arrayOf(Coordinate(1, 1), Coordinate(-1, 1), Coordinate(1, -1), Coordinate(-1, -1))
	var ROOK_MOVES: Array<Coordinate> =
		arrayOf(Coordinate(0, 1), Coordinate(0, -1), Coordinate(1, 0), Coordinate(-1, 0))
	var QUEEN_MOVES: Array<Coordinate> = arrayOf(
		Coordinate(0, 1),
		Coordinate(0, -1),
		Coordinate(1, 0),
		Coordinate(-1, 0),
		Coordinate(1, 1),
		Coordinate(-1, 1),
		Coordinate(1, -1),
		Coordinate(-1, -1)
	)
	private var BLACK_PAWN_NORMAL_MOVES = arrayOf(Coordinate(0, 1))
	private var WHITE_PAWN_NORMAL_MOVES = arrayOf(Coordinate(0, -1))
	private var WHITE_PAWN_ATTACK_MOVES = arrayOf(Coordinate(1, -1), Coordinate(-1, -1))
	private var BLACK_PAWN_ATTACK_MOVES = arrayOf(Coordinate(1, 1), Coordinate(-1, 1))
	private var BLACK_PAWN_START_MOVES = arrayOf(Coordinate(0, 2))
	private var WHITE_PAWN_START_MOVES = arrayOf(Coordinate(0, -2))
	private var BLACK_PAWNS_START_Y_POS = 1
	private var WHITE_PAWNS_START_Y_POS = 6
	var PAWN_MOVES: MutableMap<PieceTeams, MutableMap<String, Array<Coordinate>>> = EnumMap(PieceTeams::class.java)

	init {
		val whitePawnMoves: MutableMap<String, Array<Coordinate>> = HashMap()
		val blackPawnMoves: MutableMap<String, Array<Coordinate>> = HashMap()
		whitePawnMoves["Normal"] = WHITE_PAWN_NORMAL_MOVES
		whitePawnMoves["Attack"] = WHITE_PAWN_ATTACK_MOVES
		whitePawnMoves["Start"] = WHITE_PAWN_START_MOVES
		blackPawnMoves["Normal"] = BLACK_PAWN_NORMAL_MOVES
		blackPawnMoves["Attack"] = BLACK_PAWN_ATTACK_MOVES
		blackPawnMoves["Start"] = BLACK_PAWN_START_MOVES
		PAWN_MOVES[PieceTeams.WHITE] = whitePawnMoves
		PAWN_MOVES[PieceTeams.BLACK] = blackPawnMoves
	}

	fun getPawnStartPosY(team: PieceTeams): Int {
		return if (team === PieceTeams.WHITE) {
			WHITE_PAWNS_START_Y_POS
		} else {
			BLACK_PAWNS_START_Y_POS
		}
	}
}