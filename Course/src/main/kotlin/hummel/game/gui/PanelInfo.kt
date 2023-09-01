package hummel.game.gui

import java.awt.*
import javax.swing.*
import javax.swing.border.EmptyBorder

class PanelInfo : JPanel() {
	val killedPiecesList: ArrayList<String> = ArrayList()
	var turnLBL: JLabel = JLabel()

	fun init() {
		border = EmptyBorder(5, 5, 5, 5)
		layout = BorderLayout(0, 0)
		layout = GridLayout(0, 1, 0, 0)

		val content = JPanel()
		turnLBL.background = Color(0, 0, 0)
		turnLBL.font = Font("Segoe UI", Font.PLAIN, 24)
		turnLBL.foreground = Color(0, 0, 0)
		turnLBL.text = "Your turn"
		turnLBL.preferredSize = Dimension(150, turnLBL.preferredSize.height)
		val histBTN = JButton()
		histBTN.font = Font("Segoe UI", Font.PLAIN, 24)
		histBTN.text = "History"
		histBTN.addActionListener {
			val sb = StringBuilder()
			for ((index, item) in killedPiecesList.withIndex()) {
				sb.append("${index + 1}. $item\r\n")
			}
			ScrollWindow("History", sb.toString())
		}
		content.add(turnLBL)
		content.add(histBTN)
		add(content)
	}
}

class ScrollWindow(name: String, data: String) : JFrame() {
	init {
		title = name
		layout = BorderLayout()
		setSize(300, 300)

		val textArea = JTextArea(data)
		textArea.font = Font("Arial", Font.PLAIN, 16)
		textArea.lineWrap = true
		textArea.wrapStyleWord = true
		textArea.caretPosition = 0

		val scrollPane = JScrollPane(textArea)
		scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS

		add(scrollPane, BorderLayout.CENTER)
		setLocationRelativeTo(null)
		isVisible = true
	}
}