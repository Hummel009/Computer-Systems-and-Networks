package hummel

fun main() {
	val functions = mapOf(
		"tcpSend" to ::tcpSend,
		"tcpReceive" to ::tcpReceive,
		"udpSend" to ::udpSend,
		"udpReceive" to ::udpReceive,
	)
	while (true) {
		print("Enter the command: ")
		val command = readln()

		if ("exit" == command) {
			break
		}

		functions[command]?.invoke() ?: println("Unknown command!")
	}
}