package hummel.msg

import java.io.Serializable

class Message(var type: MessageTypes) : Serializable {
	var content: Any? = null

	enum class MessageTypes {
		START, MOVE, END, PAIRING, CHECK, LEAVE
	}
}