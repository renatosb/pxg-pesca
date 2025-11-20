package pesca.tela

import abstracts.TelaPanel
import fileManager.ConfigIni
import java.awt.GridLayout
import javax.swing.JFrame

object Tela : JFrame() {
    private fun readResolve(): Any = Tela
    private val telasPanel: List<TelaPanel> = listOf(
        PanelInfo,
        PanelCheckbox,
        PanelCheckboxRotina,
        PanelButton,
    )

    init {
        title = "Pxg Pesca"
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(250, 700)
        isResizable = false
        layout = GridLayout(telasPanel.size, 1)
        isAlwaysOnTop = true

        telasPanel.map { add(it.getPanel()) }
        ConfigIni.loadConfigs()

        setLocationRelativeTo(null)
        isVisible = true
    }
}
