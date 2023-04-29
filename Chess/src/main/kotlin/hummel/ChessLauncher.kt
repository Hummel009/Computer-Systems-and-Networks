package hummel

import hummel.game.gui.ClientGUI
import hummel.side_server.Server
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GridLayout
import java.net.InetAddress
import javax.swing.*
import javax.swing.border.EmptyBorder

fun main() {
	EventQueue.invokeLater {
		try {
			for (info in UIManager.getInstalledLookAndFeels()) {
				if ("Windows Classic" == info.name) {
					UIManager.setLookAndFeel(info.className)
					break
				}
			}
			val frame = GUI()
			frame.isVisible = true
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}

class GUI : JFrame() {
	init {
		title = "Chess Launcher"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 550, 200)

		val panelContent = JPanel()
		panelContent.border = EmptyBorder(5, 5, 5, 5)
		panelContent.layout = BorderLayout(0, 0)
		panelContent.layout = GridLayout(0, 1, 0, 0)
		contentPane = panelContent

		val addressPanel = JPanel()
		val addressDesc = JLabel("Address:")
		addressDesc.preferredSize = Dimension(60, addressDesc.preferredSize.height)
		val addressValue = JTextField(24)
		addressValue.text = "127.0.0.1"
		addressPanel.add(addressDesc)
		addressPanel.add(addressValue)

		val portPanel = JPanel()
		val portDesc = JLabel("Port:")
		portDesc.preferredSize = Dimension(60, portDesc.preferredSize.height)
		val portValue = JTextField(24)
		portValue.text = "5000"
		portPanel.add(portDesc)
		portPanel.add(portValue)

		val buttonPanel = JPanel()
		val buttonServer = JButton("Start Server")
		val buttonClient = JButton("Start Client")
		buttonServer.addActionListener { startServer(portValue) }
		buttonClient.addActionListener { startClient(addressValue, portValue) }
		buttonPanel.add(buttonServer)
		buttonPanel.add(buttonClient)

		panelContent.add(addressPanel)
		panelContent.add(portPanel)
		panelContent.add(buttonPanel)

		setLocationRelativeTo(null)
	}

	private fun startServer(portValue: JTextField) {
		val server = Server(portValue.text.toInt())
		server.listenClientConnectionRequests()
		println("Server is ON; port: ${portValue.text}")
	}

	private fun startClient(addressValue: JTextField, portValue: JTextField) {
		val table1 = ClientGUI(addressValue.text, portValue.text.toInt())
		table1.init()
	}
}