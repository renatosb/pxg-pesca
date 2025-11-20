package pescaConfig.config

import pescaConfig.tela.Tela
import abstracts.TelaPanel
import java.awt.GridLayout
import javax.swing.JFrame

object TelaConfig: JFrame() {
    private fun readResolve(): Any = TelaConfig
    private val telasPanel: List<TelaPanel> = listOf(
        PanelConfigInfo,
        PanelConfigButton
    )

    init {
        title = "PXG Pesca Configuração"
        defaultCloseOperation = HIDE_ON_CLOSE
        setSize(350, 450)
        isResizable = false
        layout = GridLayout(telasPanel.size, 1)
        isAlwaysOnTop = true

        telasPanel.map { add(it.getPanel()) }

        setLocationRelativeTo(Tela)
     }
}