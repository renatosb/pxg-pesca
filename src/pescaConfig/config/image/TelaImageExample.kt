package pescaConfig.config.image

import abstracts.TelaPanel
import pescaConfig.config.TelaConfig
import java.awt.GridLayout
import javax.swing.JFrame

object TelaImageExample: JFrame() {
    private fun readResolve(): Any = TelaConfig
    private val telasPanel: List<TelaPanel> = listOf(
        PanelConfigImageInfo,
    )

    init {
        title = "PXG Pesca Exemplo"
        defaultCloseOperation = HIDE_ON_CLOSE
        setSize(400, 500)
        isResizable = false
        layout = GridLayout(telasPanel.size, 1)
        isAlwaysOnTop = true

        telasPanel.map { add(it.getPanel()) }

        setLocationRelativeTo(TelaConfig)
    }
}