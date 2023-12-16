package hummel.game.gui

import hummel.GUI
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel

class PanelMenu : JPanel() {
	val infoLBL: JLabel = JLabel()
	val playBTN: JLabel = JLabel()
	val playIcon: ImageIcon
		get() {
			val imageStream = GUI::class.java.getResourceAsStream("/img/icon.png")
			val originalImage = ImageIO.read(imageStream)
			return ImageIcon(originalImage)
		}
	val playIconHover: ImageIcon
		get() {
			val imageStream = GUI::class.java.getResourceAsStream("/img/icon_hover.png")
			val originalImage = ImageIO.read(imageStream)
			return ImageIcon(originalImage)
		}

	fun init() {
		playBTN.icon = playIcon
		add(playBTN)
		add(infoLBL)
	}
}