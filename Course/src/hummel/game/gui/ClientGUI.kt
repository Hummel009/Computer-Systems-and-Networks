package hummel.game.gui

import hummel.game.Data
import hummel.game.board.Board
import hummel.game.piece.PieceTeams
import hummel.msg.Message
import hummel.side_client.Client
import java.awt.BorderLayout
import java.awt.Color
import java.awt.EventQueue
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JFrame

class ClientGUI(serverIP: String, serverPort: Int) {
	private var client = Client(this, serverIP, serverPort)
	var gameFrame: JFrame = JFrame("Chess")
	var mainMenu: PanelMenu = PanelMenu()
	lateinit var boardPanel: PanelBoard
	lateinit var chessBoard: Board
	lateinit var bottomGameMenu: PanelInfo

	fun init() {
		EventQueue.invokeLater {
			try {
				gameFrame.layout = BorderLayout()
				gameFrame.size = Data.OUTER_FRAME_DIMENSION
				client.init()
				createMainMenu()
				gameFrame.isVisible = true
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	fun createMainMenu() {
		mainMenu.init()
		mainMenu.infoLBL.text = ""
		mainMenu.infoLBL.isVisible = false

		mainMenu.playBTN.addMouseListener(object : MouseAdapter() {
			override fun mouseClicked(e: MouseEvent?) {
				if (!client.isPaired) {
					mainMenu.infoLBL.isVisible = true
					mainMenu.infoLBL.text = "Matching..."
					mainMenu.playBTN.isEnabled = false
					val msg = Message(Message.MessageTypes.PAIRING)
					msg.content = "MATCH"
					client.sendToServer(msg)
				}
				if (client.isPaired) {
					mainMenu.infoLBL.text = "Matched"
					mainMenu.playBTN.isEnabled = true
					mainMenu.infoLBL.text = ""
					mainMenu.infoLBL.isVisible = false
					createGamePanel()
				}
			}

			override fun mouseEntered(e: MouseEvent?) {
				mainMenu.playBTN.icon = mainMenu.playIconHover
			}

			override fun mouseExited(e: MouseEvent?) {
				mainMenu.playBTN.icon = mainMenu.playIcon
			}
		})
		gameFrame.add(mainMenu, BorderLayout.CENTER)
	}

	private fun createGamePanel() {
		gameFrame.remove(mainMenu)
		chessBoard = Board()
		boardPanel = PanelBoard(chessBoard, client)
		boardPanel.init()
		bottomGameMenu = PanelInfo()
		bottomGameMenu.init()
		if (client.team === PieceTeams.WHITE) {
			bottomGameMenu.turnLBL.text = "Your Turn"
			bottomGameMenu.turnLBL.foreground = Color.BLACK
		} else {
			bottomGameMenu.turnLBL.text = "Enemy Turn"
			bottomGameMenu.turnLBL.foreground = Color.RED
		}
		gameFrame.add(boardPanel)
		gameFrame.add(boardPanel, BorderLayout.CENTER)
		gameFrame.add(bottomGameMenu, BorderLayout.PAGE_END)
		gameFrame.isVisible = true
	}
}