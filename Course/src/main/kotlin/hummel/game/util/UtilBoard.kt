package hummel.game.util

import hummel.GUI
import hummel.game.Data
import hummel.game.board.Tile
import hummel.game.board.TileNull
import hummel.game.piece.*
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger
import javax.imageio.ImageIO
import javax.swing.ImageIcon


object UtilBoard {
	fun isValidCoordinate(coord: Coordinate): Boolean {
		return coord.x >= Data.BOARD_LOWER_BOUND && coord.x <= Data.BOARD_UPPER_BOUND && coord.y >= Data.BOARD_LOWER_BOUND && coord.y <= Data.BOARD_UPPER_BOUND
	}

	fun getImageOfTeamPiece(team: PieceTeams, pieceType: PieceTypes?): ImageIcon? {
		var imagePath = "img/"
		if (pieceType == null) {
			imagePath += "transparent.png"
		} else {
			imagePath += if (team === PieceTeams.BLACK) "black" else "white"
			if (pieceType === PieceTypes.BISHOP) {
				imagePath += "_bishop.png"
			} else if (pieceType === PieceTypes.KING) {
				imagePath += "_king.png"
			} else if (pieceType === PieceTypes.QUEEN) {
				imagePath += "_queen.png"
			} else if (pieceType === PieceTypes.KNIGHT) {
				imagePath += "_knight.png"
			} else if (pieceType === PieceTypes.PAWN) {
				imagePath += "_pawn.png"
			} else if (pieceType === PieceTypes.ROOK) {
				imagePath += "_rook.png"
			}
		}
		try {
			val imageStream = GUI::class.java.getResourceAsStream("/$imagePath")
			val originalImage = ImageIO.read(imageStream)
			println(originalImage)
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