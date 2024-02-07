package com.github.hummel.csn.lab3

fun main() {
	val functions = mapOf(
		"launchDayTimeServer" to ::launchDayTimeServer, "launchDayTimeClient" to ::launchDayTimeClient
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