package pesca.tela

import abstracts.TelaPanel
import pesca.tela.PanelInfo.updateFishPositionText
import pescaLogic.SqmUtils
import java.awt.GridLayout
import java.awt.Point
import javax.swing.ButtonGroup
import javax.swing.JCheckBox
import javax.swing.JPanel

object PanelCheckbox : TelaPanel {
    private val panel: JPanel = JPanel()
    private val colunas: Int = 11
    private lateinit var sqmPesca: Point

    init {

        panel.layout = GridLayout(0, colunas)
        createRadioButtons()
    }

    private fun createRadioButtons() {
        val group = ButtonGroup()
        val maxCheckbox = colunas * colunas
        for (index in 1..maxCheckbox) {
            val radio = JCheckBox()
            if(index == maxCheckbox/2 + 1) {
                radio.isEnabled = false
             }
            radio.addActionListener {
                if (radio.isSelected) {
                    sqmPesca = SqmUtils.getSqmByCheckBox(index, colunas)
                    updateFishPositionText(sqmPesca)
                }
            }
            group.add(radio)
            panel.add(radio)
        }
    }

    override fun getPanel(): JPanel {
        return panel
    }

    fun getSqmPesca(): Point {
        return sqmPesca
    }
}