package com.github.hummel.csn.course.game.piece

import java.io.Serializable

class Coordinate(val x: Int, val y: Int) : Serializable {
	operator fun plus(coord: Coordinate): Coordinate = Coordinate(x + coord.x, y + coord.y)

	override fun hashCode(): Int {
		var result = x
		result = 31 * result + y
		return result
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Coordinate

		if (x != other.x) return false
		if (y != other.y) return false

		return true
	}

	override fun toString(): String = "Coordinate(x=$x, y=$y)"
}