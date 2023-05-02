package hummel.game.gui

import java.awt.Color
import java.awt.Font
import javax.swing.*

class PanelInfo : JPanel() {
	var turnLBL = JLabel()

	fun init() {
		turnLBL.background = Color(0, 0, 0)
		turnLBL.font = Font("Segoe UI", Font.PLAIN, 24)
		turnLBL.foreground = Color(0, 0, 0)
		turnLBL.text = "Your turn"
		val layout = GroupLayout(this)
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(23, 23, 23).addGroup(
					layout.createParallelGroup(
						GroupLayout.Alignment.LEADING
					)
				).addGap(99, 99, 99).addComponent(turnLBL).addContainerGap(80, Short.MAX_VALUE.toInt())
			)
		)
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap().addGroup(
					layout.createParallelGroup(
						GroupLayout.Alignment.BASELINE
					).addComponent(turnLBL)
				)
			)
		)
	}
}