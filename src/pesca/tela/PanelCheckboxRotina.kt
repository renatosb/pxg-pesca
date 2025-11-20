package pesca.tela

import abstracts.TelaPanel
import java.awt.GridLayout
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

object PanelCheckboxRotina : TelaPanel {
    private val panel = JPanel()
    private val tituloLabel = JLabel("<html> ----  Rotina  ----<br> Somente pesca</html>")
    private val usarReviveCheckbox = JCheckBox("Usar Revive")
    private val atacarCheckbox = JCheckBox("Atacar")
    private val atacarSelectBox = JComboBox(arrayOf(1,2,3,4,5,6,7,8,9))
    private val coletarLootCheckbox = JCheckBox("Coletar Loot")
    private val jogarBallCheckbox = JCheckBox("Jogar Ball")
    private val atacarPanel = JPanel()
    private val listaRotina: List<JComponent> = listOf(
        tituloLabel,
        usarReviveCheckbox,
        atacarPanel,
        coletarLootCheckbox,
        jogarBallCheckbox
    )


    init {
        panel.layout = GridLayout(listaRotina.size, 1)
        atacarPanel.layout = GridLayout(1, 2)
        atacarPanel.add(atacarCheckbox)
        atacarPanel.add(atacarSelectBox)
        listaRotina.map { panel.add(it) }
    }

    override fun getPanel(): JPanel {
        return panel
    }

    fun getNumAtaqueSelected(): Int{
        return atacarSelectBox.selectedItem as Int
    }

    fun usarRevive(): Boolean {
        return usarReviveCheckbox.isSelected
    }

    fun atacar(): Boolean {
        return atacarCheckbox.isSelected
    }

    fun coletarLoot(): Boolean {
        return coletarLootCheckbox.isSelected
    }

    fun jogarBall(): Boolean {
        return jogarBallCheckbox.isSelected
    }
}