package com.github.hummel.csn.course.game.gui

import com.github.hummel.csn.course.game.Data
import com.github.hummel.csn.course.game.board.Board
import com.github.hummel.csn.course.game.piece.Coordinate
import com.github.hummel.csn.course.side_client.Client
import java.awt.GridLayout
import javax.swing.JPanel

class PanelBoard(
	private var chessBoard: Board, private var client: Client
) : JPanel(GridLayout(Data.ROW_COUNT, Data.ROW_TILE_COUNT)) {
	private var boardTiles = Array<Array<PanelTile?>>(Data.ROW_COUNT) { arrayOfNulls(Data.ROW_TILE_COUNT) }

	fun init() {
		for (i in 0 until Data.ROW_COUNT) {
			for (j in 0 until Data.ROW_TILE_COUNT) {
				val tilePanel = PanelTile(this, Coordinate(j, i), chessBoard, client)
				boardTiles[i][j] = tilePanel
				add(tilePanel)
			}
		}
	}

	fun updateBoardGUI(board: Board) {
		for (i in 0 until Data.ROW_COUNT) {
			for (j in 0 until Data.ROW_TILE_COUNT) {
				(boardTiles[i][j] ?: return).assignTileColor(board)
				(boardTiles[i][j] ?: return).assignTilePieceIcon(board)
			}
		}
	}
}