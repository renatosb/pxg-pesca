package pescaConfig.tela

import abstracts.TelaPanel
import java.awt.GridLayout
import java.awt.Point
import javax.swing.JLabel
import javax.swing.JPanel

object PanelInfo : TelaPanel {

    private val panel: JPanel = JPanel()
    private val centerPosition: JLabel = JLabel("Centro da Tela: ")
    private val listaPositions: List<JLabel> = listOf(
        centerPosition,
    )

    init {
        panel.layout = GridLayout(listaPositions.size, 1)
        listaPositions.map { panel.add(it) }
    }

    override fun getPanel(): JPanel {
        return panel
    }

    fun updateCenterPositionText(point: Point) {
        centerPosition.text = "Centro da Tela: ${point.x}, ${point.y}"
    }
}