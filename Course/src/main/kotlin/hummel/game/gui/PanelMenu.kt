package hummel.game.gui

import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel

class PanelMenu : JPanel() {
	val infoLBL: JLabel = JLabel()
	val playBTN: JLabel = JLabel()
	val playIcon: ImageIcon = ImageIcon("src/hummel/game/img/icon.png")
	val playIconHover: ImageIcon = ImageIcon("src/hummel/game/img/icon_hover.png")

	fun init() {
		playBTN.icon = playIcon
		add(playBTN)
		add(infoLBL)
	}
}