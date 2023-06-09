package hummel

import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*

class Worker(private val controlSocket: Socket, private val dataPort: Int) : Thread() {
	private val debugMode = true

	private enum class TransferType {
		ASCII, BINARY
	}

	private enum class UserStatus {
		NOT_LOGGED_IN, ENTERED_USER_NAME, LOGGED_IN
	}

	private val root: String
	private var currDirectory: String
	private val fileSeparator = "/"
	private var controlOutWriter: PrintWriter? = null
	private var controlIn: BufferedReader? = null

	private var dataSocket: ServerSocket? = null
	private var dataConnection: Socket? = null
	private var dataOutWriter: PrintWriter? = null
	private var transferMode = TransferType.ASCII

	private var currentUserStatus = UserStatus.NOT_LOGGED_IN
	private val validUser = "amogus"
	private val validPassword = "sus"
	private var quitCommandLoop = false

	init {
		currDirectory = System.getProperty("user.dir") + "/test"
		this.root = System.getProperty("user.dir")
	}

	override fun run() {
		debugOutput("Current working directory $currDirectory")
		try {
			controlIn = BufferedReader(InputStreamReader(controlSocket.getInputStream()))
			controlOutWriter = PrintWriter(controlSocket.getOutputStream(), true)
			sendMsgToClient("220 Welcome to the COMP4621 FTP-Server")
			while (!quitCommandLoop) {
				executeCommand(controlIn!!.readLine())
			}
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			try {
				controlIn!!.close()
				controlOutWriter!!.close()
				controlSocket.close()
				debugOutput("Sockets closed and worker stopped")
			} catch (e: IOException) {
				e.printStackTrace()
				debugOutput("Could not close sockets")
			}
		}
	}

	private fun executeCommand(c: String) {
		val index = c.indexOf(' ')
		val command =
			if (index == -1) c.uppercase(Locale.getDefault()) else c.substring(0, index).uppercase(Locale.getDefault())
		val args = if (index == -1) null else c.substring(index + 1)
		debugOutput("Command: $command Args: $args")
		when (command) {
			"USER" -> handleUser(args)
			"PASS" -> handlePass(args)
			"CWD" -> handleCwd(args)
			"LIST" -> handleNlst(args)
			"PWD", "XPWD" -> handlePwd()
			"QUIT" -> handleQuit()
			"PASV" -> handlePasv()
			"EPSV" -> handleEpsv()
			"SYST" -> handleSyst()
			"FEAT" -> handleFeat()
			"PORT" -> handlePort(args)
			"EPRT" -> handleEPort(args)
			"RETR" -> handleRetr(args)
			"MKD", "XMKD" -> handleMkd(args)
			"RMD", "XRMD" -> handleRmd(args)
			"TYPE" -> handleType(args)
			"STOR" -> handleStor(args)
			else -> sendMsgToClient("501 Unknown command")
		}
	}

	private fun sendMsgToClient(msg: String) {
		controlOutWriter!!.println(msg)
	}

	private fun sendDataMsgToClient(msg: String?) {
		if (dataConnection == null || dataConnection!!.isClosed) {
			sendMsgToClient("425 No data connection was established")
			debugOutput("Cannot send message, because no data connection is established")
		} else {
			dataOutWriter!!.print(
				"""
	$msg
	
	""".trimIndent()
			)
		}
	}

	private fun openDataConnectionPassive(port: Int) {
		try {
			dataSocket = ServerSocket(port)
			dataConnection = dataSocket!!.accept()
			dataOutWriter = PrintWriter(dataConnection!!.getOutputStream(), true)
			debugOutput("Data connection - Passive Mode - established")
		} catch (e: IOException) {
			debugOutput("Could not create data connection.")
			e.printStackTrace()
		}
	}

	private fun openDataConnectionActive(ipAddress: String, port: Int) {
		try {
			dataConnection = Socket(ipAddress, port)
			dataOutWriter = PrintWriter(dataConnection!!.getOutputStream(), true)
			debugOutput("Data connection - Active Mode - established")
		} catch (e: IOException) {
			debugOutput("Could not connect to client data socket")
			e.printStackTrace()
		}
	}

	private fun closeDataConnection() {
		try {
			dataOutWriter!!.close()
			dataConnection!!.close()
			if (dataSocket != null) {
				dataSocket!!.close()
			}
			debugOutput("Data connection was closed")
		} catch (e: IOException) {
			debugOutput("Could not close data connection")
			e.printStackTrace()
		}
		dataOutWriter = null
		dataConnection = null
		dataSocket = null
	}

	private fun handleUser(username: String?) {
		if (username!!.lowercase(Locale.getDefault()) == validUser) {
			sendMsgToClient("331 User name okay, need password")
			currentUserStatus = UserStatus.ENTERED_USER_NAME
		} else if (currentUserStatus == UserStatus.LOGGED_IN) {
			sendMsgToClient("530 User already logged in")
		} else {
			sendMsgToClient("530 Not logged in")
		}
	}

	private fun handlePass(password: String?) {
		if (currentUserStatus == UserStatus.ENTERED_USER_NAME && password == validPassword) {
			currentUserStatus = UserStatus.LOGGED_IN
			sendMsgToClient("230-Welcome to HKUST")
			sendMsgToClient("230 User logged in successfully")
		} else if (currentUserStatus == UserStatus.LOGGED_IN) {
			sendMsgToClient("530 User already logged in")
		} else {
			sendMsgToClient("530 Not logged in")
		}
	}

	private fun handleCwd(args: String?) {
		var filename = currDirectory

		if (args == "..") {
			val ind = filename.lastIndexOf(fileSeparator)
			if (ind > 0) {
				filename = filename.substring(0, ind)
			}
		} else if (args != null && args != ".") {
			filename = filename + fileSeparator + args
		}

		val f = File(filename)
		if (f.exists() && f.isDirectory && filename.length >= root.length) {
			currDirectory = filename
			sendMsgToClient("250 The current directory has been changed to $currDirectory")
		} else {
			sendMsgToClient("550 Requested action not taken. File unavailable.")
		}
	}

	private fun handleNlst(args: String?) {
		if (dataConnection == null || dataConnection!!.isClosed) {
			sendMsgToClient("425 No data connection was established")
		} else {
			val dirContent = nlstHelper(args)
			if (dirContent == null) {
				sendMsgToClient("550 File does not exist.")
			} else {
				sendMsgToClient("125 Opening ASCII mode data connection for file list.")
				for (i in dirContent.indices) {
					sendDataMsgToClient(dirContent[i])
				}
				sendMsgToClient("226 Transfer complete.")
				closeDataConnection()
			}
		}
	}

	private fun nlstHelper(args: String?): Array<String?>? {
		var filename = currDirectory
		if (args != null) {
			filename = filename + fileSeparator + args
		}

		val f = File(filename)
		return if (f.exists() && f.isDirectory) {
			f.list()
		} else if (f.exists() && f.isFile) {
			val allFiles = arrayOfNulls<String>(1)
			allFiles[0] = f.name
			allFiles
		} else {
			null
		}
	}

	private fun handlePort(args: String?) {
		val stringSplit = args!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
		val hostName = stringSplit[0] + "." + stringSplit[1] + "." + stringSplit[2] + "." + stringSplit[3]
		val p = stringSplit[4].toInt() * 256 + stringSplit[5].toInt()

		openDataConnectionActive(hostName, p)
		sendMsgToClient("200 Command OK")
	}

	private fun handleEPort(args: String?) {
		val splitArgs = args!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
		val ipAddress = splitArgs[2]
		val port = splitArgs[3].toInt()
		openDataConnectionActive(ipAddress, port)
		sendMsgToClient("200 Command OK")
	}

	private fun handlePwd() {
		sendMsgToClient("257 \"$currDirectory\"")
	}

	private fun handlePasv() {
		val myIp = "127.0.0.1"
		val myIpSplit = myIp.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
		val p1 = dataPort / 256
		val p2 = dataPort % 256
		sendMsgToClient("227 Entering Passive Mode (" + myIpSplit[0] + "," + myIpSplit[1] + "," + myIpSplit[2] + "," + myIpSplit[3] + "," + p1 + "," + p2 + ")")
		openDataConnectionPassive(dataPort)
	}

	private fun handleEpsv() {
		sendMsgToClient("229 Entering Extended Passive Mode (|||$dataPort|)")
		openDataConnectionPassive(dataPort)
	}

	private fun handleQuit() {
		sendMsgToClient("221 Closing connection")
		quitCommandLoop = true
	}

	private fun handleSyst() {
		sendMsgToClient("215 COMP4621 FTP Server Homebrew")
	}

	private fun handleFeat() {
		sendMsgToClient("211-Extensions supported:")
		sendMsgToClient("211 END")
	}

	private fun handleMkd(args: String?) {
		if (args != null && args.matches("^[a-zA-Z0-9]+$".toRegex())) {
			val dir = File(currDirectory + fileSeparator + args)
			if (!dir.mkdir()) {
				sendMsgToClient("550 Failed to create new directory")
				debugOutput("Failed to create new directory")
			} else {
				sendMsgToClient("250 Directory successfully created")
			}
		} else {
			sendMsgToClient("550 Invalid name")
		}
	}

	private fun handleRmd(dir: String?) {
		var filename = currDirectory

		if (dir != null && dir.matches("^[a-zA-Z0-9]+$".toRegex())) {
			filename = filename + fileSeparator + dir

			val d = File(filename)
			if (d.exists() && d.isDirectory) {
				d.delete()
				sendMsgToClient("250 Directory was successfully removed")
			} else {
				sendMsgToClient("550 Requested action not taken. File unavailable.")
			}
		} else {
			sendMsgToClient("550 Invalid file name.")
		}
	}

	private fun handleType(mode: String?) {
		if (mode!!.uppercase(Locale.getDefault()) == "A") {
			transferMode = TransferType.ASCII
			sendMsgToClient("200 OK")
		} else if (mode.uppercase(Locale.getDefault()) == "I") {
			transferMode = TransferType.BINARY
			sendMsgToClient("200 OK")
		} else sendMsgToClient("504 Not OK")
	}

	private fun handleRetr(file: String?) {
		val f = File(currDirectory + fileSeparator + file)
		if (!f.exists()) {
			sendMsgToClient("550 File does not exist")
		} else {
			if (transferMode == TransferType.BINARY) {
				var fout: BufferedOutputStream? = null
				var fin: BufferedInputStream? = null
				sendMsgToClient("150 Opening binary mode data connection for requested file " + f.name)
				try {
					fout = BufferedOutputStream(dataConnection!!.getOutputStream())
					fin = BufferedInputStream(FileInputStream(f))
				} catch (e: Exception) {
					debugOutput("Could not create file streams")
				}
				debugOutput("Starting file transmission of " + f.name)

				val buf = ByteArray(1024)
				var l: Int
				try {
					while (fin!!.read(buf, 0, 1024).also { l = it } != -1) {
						fout!!.write(buf, 0, l)
					}
				} catch (e: IOException) {
					debugOutput("Could not read from or write to file streams")
					e.printStackTrace()
				}

				try {
					fin!!.close()
					fout!!.close()
				} catch (e: IOException) {
					debugOutput("Could not close file streams")
					e.printStackTrace()
				}
				debugOutput("Completed file transmission of " + f.name)
				sendMsgToClient("226 File transfer successful. Closing data connection.")
			} else {
				sendMsgToClient("150 Opening ASCII mode data connection for requested file " + f.name)
				var rin: BufferedReader? = null
				var rout: PrintWriter? = null
				try {
					rin = BufferedReader(FileReader(f))
					rout = PrintWriter(dataConnection!!.getOutputStream(), true)
				} catch (e: IOException) {
					debugOutput("Could not create file streams")
				}
				var s: String?
				try {
					while (rin!!.readLine().also { s = it } != null) {
						rout!!.println(s)
					}
				} catch (e: IOException) {
					debugOutput("Could not read from or write to file streams")
					e.printStackTrace()
				}
				try {
					rout!!.close()
					rin!!.close()
				} catch (e: IOException) {
					debugOutput("Could not close file streams")
					e.printStackTrace()
				}
				sendMsgToClient("226 File transfer successful. Closing data connection.")
			}
		}
		closeDataConnection()
	}

	private fun handleStor(file: String?) {
		if (file == null) {
			sendMsgToClient("501 No filename given")
		} else {
			val f = File(currDirectory + fileSeparator + file)
			if (f.exists()) {
				sendMsgToClient("550 File already exists")
			} else {
				if (transferMode == TransferType.BINARY) {
					var fout: BufferedOutputStream? = null
					var fin: BufferedInputStream? = null
					sendMsgToClient("150 Opening binary mode data connection for requested file " + f.name)
					try {
						fout = BufferedOutputStream(FileOutputStream(f))
						fin = BufferedInputStream(dataConnection!!.getInputStream())
					} catch (e: Exception) {
						debugOutput("Could not create file streams")
					}
					debugOutput("Start receiving file " + f.name)

					val buf = ByteArray(1024)
					var l: Int
					try {
						while (fin!!.read(buf, 0, 1024).also { l = it } != -1) {
							fout!!.write(buf, 0, l)
						}
					} catch (e: IOException) {
						debugOutput("Could not read from or write to file streams")
						e.printStackTrace()
					}

					try {
						fin!!.close()
						fout!!.close()
					} catch (e: IOException) {
						debugOutput("Could not close file streams")
						e.printStackTrace()
					}
					debugOutput("Completed receiving file " + f.name)
					sendMsgToClient("226 File transfer successful. Closing data connection.")
				} else {
					sendMsgToClient("150 Opening ASCII mode data connection for requested file " + f.name)
					var rin: BufferedReader? = null
					var rout: PrintWriter? = null
					try {
						rin = BufferedReader(InputStreamReader(dataConnection!!.getInputStream()))
						rout = PrintWriter(FileOutputStream(f), true)
					} catch (e: IOException) {
						debugOutput("Could not create file streams")
					}
					var s: String?
					try {
						while (rin!!.readLine().also { s = it } != null) {
							rout!!.println(s)
						}
					} catch (e: IOException) {
						debugOutput("Could not read from or write to file streams")
						e.printStackTrace()
					}
					try {
						rout!!.close()
						rin!!.close()
					} catch (e: IOException) {
						debugOutput("Could not close file streams")
						e.printStackTrace()
					}
					sendMsgToClient("226 File transfer successful. Closing data connection.")
				}
			}
			closeDataConnection()
		}
	}

	private fun debugOutput(msg: String) {
		if (debugMode) {
			println("Thread " + this.id + ": " + msg)
		}
	}
}