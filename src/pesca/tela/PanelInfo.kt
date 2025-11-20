package pesca.tela

import abstracts.TelaPanel
import java.awt.GridLayout
import java.awt.Point
import javax.swing.ButtonGroup
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JRadioButton

object PanelInfo : TelaPanel {

    private val panel: JPanel = JPanel()
    private val centerPositionLabel: JLabel = JLabel("Centro da Tela: ")
    private val fishPositionLabel: JLabel = JLabel("Posição de Pesca: ")
    private val botLogLabel: JLabel = JLabel()
    private val listaPositions: List<JLabel> = listOf(
        centerPositionLabel,
        fishPositionLabel,
        botLogLabel
    )

    init {
        panel.layout = GridLayout(listaPositions.size, 1)
        listaPositions.map { panel.add(it) }
     }

    override fun getPanel(): JPanel {
        return panel
    }

    fun updateCenterPositionText(point: Point) {
        centerPositionLabel.text = "Centro da Tela: ${point.x}, ${point.y}"
    }

    fun updateFishPositionText(point: Point) {
        fishPositionLabel.text = "Posição de Pesca: ${point.x}, ${point.y}"
    }

    fun updateBotLogText(stateEnum: StateEnum) {
        botLogLabel.text = "----- ${stateEnum.description} -----"
    }
}