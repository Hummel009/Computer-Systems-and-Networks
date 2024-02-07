package com.github.hummel.csn.lab1

import java.net.NetworkInterface

fun main() {
	val interfaces = NetworkInterface.getNetworkInterfaces()
	while (interfaces.hasMoreElements()) {
		val networkInterface = interfaces.nextElement()
		val mac = networkInterface.hardwareAddress
		mac?.let {
			print("MAC Address of " + networkInterface.name + ": ")
			println(it.joinToString("-") { byte -> "%02X".format(byte) })
		}
	}
}