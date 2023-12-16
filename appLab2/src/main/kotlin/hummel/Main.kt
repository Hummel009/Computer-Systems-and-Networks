package hummel

fun main() {
	val functions = mapOf(
		"launchClientTCP" to ::launchClientTCP,
		"launchServerTCP" to ::launchServerTCP,
		"launchClientUDP" to ::launchClientUDP,
		"launchServerUDP" to ::launchServerUDP,
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