package hummel

import java.net.InetAddress
import java.net.Socket
import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
	// Установка соединения с сервером
	val serverAddress = InetAddress.getByName("192.168.43.177")
	val serverPort = 6061
	val tcpSocket = Socket(serverAddress, serverPort)

	// Генерация случайных данных
	val random = Random()
	val packetSize = 1024
	val data = Array(1024) { ByteArray(1024) }
	for (i in 0 until 1024) {
		random.nextBytes(data[i])
	}

	// Отправка данных и замер времени передачи
	val time = measureTimeMillis {
		val outputStream = tcpSocket.getOutputStream()
		val inputStream = tcpSocket.getInputStream()

		for (i in 0 until 1024) {
			// Отправка пакета
			outputStream.write(data[i])
			outputStream.flush()

			// Получение результата
			val result = inputStream.read()
			if (result != 0) {
				println("Пакет $i: ошибка при передаче данных")
			}
		}
	}

	// Вывод результатов
	val speed = packetSize * 1024 / (time / 1000.0)
	println("Скорость передачи: $speed байт/с")
	tcpSocket.close()
}