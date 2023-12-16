package hummel.game.piece

import java.io.Serializable

enum class PieceTeams : Serializable {
	WHITE {
		override fun toString(): String = "White"
	},
	BLACK {
		override fun toString(): String = "Black"
	},
	NO_COLOR {
		override fun toString(): String = "No Color"
	}
}