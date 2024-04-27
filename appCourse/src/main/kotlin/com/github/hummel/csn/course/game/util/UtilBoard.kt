package com.github.hummel.csn.course.game.util

import com.github.hummel.csn.course.GUI
import com.github.hummel.csn.course.game.Data
import com.github.hummel.csn.course.game.board.Tile
import com.github.hummel.csn.course.game.board.TileNull
import com.github.hummel.csn.course.game.piece.*
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger
import javax.imageio.ImageIO
import javax.swing.ImageIcon


object UtilBoard {
	fun isValidCoordinate(coord: Coordinate): Boolean =
		coord.x >= Data.BOARD_LOWER_BOUND && coord.x <= Data.BOARD_UPPER_BOUND && coord.y >= Data.BOARD_LOWER_BOUND && coord.y <= Data.BOARD_UPPER_BOUND

	fun getImageOfTeamPiece(team: PieceTeams, pieceType: PieceTypes?): ImageIcon? {
		val imagePath = "img/" + if (pieceType == null) {
			"transparent.png"
		} else {
			(if (team == PieceTeams.BLACK) "black" else "white") + when (pieceType) {
				PieceTypes.BISHOP -> "_bishop.png"
				PieceTypes.KING -> "_king.png"
				PieceTypes.QUEEN -> "_queen.png"
				PieceTypes.KNIGHT -> "_knight.png"
				PieceTypes.PAWN -> "_pawn.png"
				PieceTypes.ROOK -> "_rook.png"
				PieceTypes.NULL -> ""
			}
		}
		try {
			val imageStream = GUI::class.java.getResourceAsStream("/$imagePath")
			val originalImage = ImageIO.read(imageStream)
			return ImageIcon(originalImage)
		} catch (ex: IOException) {
			Logger.getLogger(UtilBoard::class.java.name).log(Level.SEVERE, null, ex)
		}
		return null
	}

	fun createStandardBoardTiles(): Array<Array<Tile>> {
		val tiles: Array<Array<Tile>> = Array(Data.ROW_COUNT) {
			Array(Data.ROW_TILE_COUNT) { TileNull() }
		}
		tiles[0][0] = Tile(Coordinate(0, 0), PieceRook(PieceTeams.BLACK))
		tiles[1][0] = Tile(Coordinate(1, 0), PieceKnight(PieceTeams.BLACK))
		tiles[2][0] = Tile(Coordinate(2, 0), PieceBishop(PieceTeams.BLACK))
		tiles[3][0] = Tile(Coordinate(3, 0), PieceQueen(PieceTeams.BLACK))
		tiles[4][0] = Tile(Coordinate(4, 0), PieceKing(PieceTeams.BLACK))
		tiles[5][0] = Tile(Coordinate(5, 0), PieceBishop(PieceTeams.BLACK))
		tiles[6][0] = Tile(Coordinate(6, 0), PieceKnight(PieceTeams.BLACK))
		tiles[7][0] = Tile(Coordinate(7, 0), PieceRook(PieceTeams.BLACK))
		for (i in 0..7) {
			tiles[i][1] = Tile(Coordinate(i, 1), PiecePawn(PieceTeams.BLACK))
			tiles[i][6] = Tile(Coordinate(i, 6), PiecePawn(PieceTeams.WHITE))
		}
		for (i in 2..5) {
			for (j in 0..7) {
				tiles[j][i] = Tile(Coordinate(j, i), PieceNull())
			}
		}
		tiles[0][7] = Tile(Coordinate(0, 7), PieceRook(PieceTeams.WHITE))
		tiles[1][7] = Tile(Coordinate(1, 7), PieceKnight(PieceTeams.WHITE))
		tiles[2][7] = Tile(Coordinate(2, 7), PieceBishop(PieceTeams.WHITE))
		tiles[3][7] = Tile(Coordinate(3, 7), PieceKing(PieceTeams.WHITE))
		tiles[4][7] = Tile(Coordinate(4, 7), PieceQueen(PieceTeams.WHITE))
		tiles[5][7] = Tile(Coordinate(5, 7), PieceBishop(PieceTeams.WHITE))
		tiles[6][7] = Tile(Coordinate(6, 7), PieceKnight(PieceTeams.WHITE))
		tiles[7][7] = Tile(Coordinate(7, 7), PieceRook(PieceTeams.WHITE))
		return tiles
	}
}