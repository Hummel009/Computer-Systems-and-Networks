package com.github.hummel.csn.course.msg

import com.github.hummel.csn.course.game.piece.Coordinate
import java.io.Serializable

class MovementMessage : Serializable {
	var isPieceKilled: Boolean = false
	lateinit var destinationCoordinate: Coordinate
	lateinit var currentCoordinate: Coordinate
}