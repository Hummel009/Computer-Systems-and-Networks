package hummel

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
	// Установка соединения с сервером
	val serverAddress = InetAddress.getByName("192.168.43.177")
	val serverPort = 9090
	val udpSocket = DatagramSocket()

	// Генерация случайных данных
	val random = Random()
	val packetSize = 1024
	val data = Array(1024) { ByteArray(1024) }
	for (i in 0 until 1024) {
		random.nextBytes(data[i])
	}

	// Отправка данных и замер времени передачи
	val time = measureTimeMillis {
		for (i in 1 until 1024) {
			// Создание и отправка пакета
			val packet = DatagramPacket(data[i], data[i].size, serverAddress, serverPort)
			udpSocket.send(packet)

			// Получение результата
			val buffer = ByteArray(1)
			val responsePacket = DatagramPacket(buffer, buffer.size)
			udpSocket.receive(responsePacket)
			val result = buffer[0].toInt()
			if (result != 0) {
				println("Пакет $i: ошибка при передаче данных")
			}
		}
	}

	// Вывод результатов
	val speed = packetSize * 1024 / (time / 1000.0)
	println("Скорость передачи: $speed байт/с")
	udpSocket.close()
}