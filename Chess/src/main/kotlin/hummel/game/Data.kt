package hummel.game

import java.awt.Color
import java.awt.Dimension

object Data {
	var ROW_COUNT = 8
	var ROW_TILE_COUNT = 8
	var BOARD_LOWER_BOUND = 0
	var BOARD_UPPER_BOUND = 7
	var TILE_SIZE = 60
	var OUTER_FRAME_DIMENSION = Dimension(600, 700)
	var cellLight = Color(174, 104, 55)
	var cellDark = Color(220, 187, 144)
	val cellHover = Color(105, 63, 33)
}