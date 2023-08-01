package hummel.game

import java.awt.Color
import java.awt.Dimension

object Data {
	var ROW_COUNT: Int = 8
	var ROW_TILE_COUNT: Int = 8
	var BOARD_LOWER_BOUND: Int = 0
	var BOARD_UPPER_BOUND: Int = 7
	var TILE_SIZE: Int = 60
	var OUTER_FRAME_DIMENSION: Dimension = Dimension(600, 700)
	var cellLight: Color = Color(174, 104, 55)
	var cellDark: Color = Color(220, 187, 144)
	val cellHover: Color = Color(105, 63, 33)
}