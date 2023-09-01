package hummel.game.piece

import java.io.Serializable

class Coordinate(val x: Int, val y: Int) : Serializable {
	override fun toString(): String = "[X:$x, Y:$y]"

	override fun equals(other: Any?): Boolean = ((other as Coordinate?)!!.x == x) && (other!!.y == y)

	operator fun plus(coord: Coordinate): Coordinate = Coordinate(x + coord.x, y + coord.y)

	override fun hashCode(): Int {
		var result = x
		result = 31 * result + y
		return result
	}
}