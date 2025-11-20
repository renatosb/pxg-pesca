package pescaConfig.tela

import abstracts.TelaPanel
import pescaLogic.ScreenUtils
import pescaConfig.config.TelaConfig
import java.awt.Color
import java.awt.GridLayout
import java.awt.Robot
import javax.swing.JButton
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.Timer

object PanelButton : TelaPanel {

    private val panel: JPanel = JPanel()
    private val robot: Robot = Robot()
    private val checkScreenCenterButton: JButton = JButton("Checar Centro da Tela")
    private val openConfigButton: JButton = JButton(" /\\ Configurar Pesca /\\")
    private val listaButtons: List<JButton> = listOf(
        checkScreenCenterButton,
        openConfigButton,
    )

    init {
        panel.layout = GridLayout(listaButtons.size, 1)
        listaButtons.map { panel.add(it) }

        checkScreenCenterButton.addActionListener {
            val delay = Timer(1000) {
                try {
                    robot.mouseMove(ScreenUtils.getSqmScreenCenter().x, ScreenUtils.getSqmScreenCenter().y)
                } catch (e: Exception) {
                    JOptionPane.showMessageDialog(
                        panel,
                        "Erro ao calcular a posiçao do centro da pescaConfig.tela. Certifique-se de ter definido as posicões de moeda e valor do raio corretamente.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    )
                }
            }
            delay.isRepeats = false
            delay.start()
        }

        openConfigButton.background = Color.green
        openConfigButton.addActionListener {
            TelaConfig.isVisible = true
        }
    }

    override fun getPanel(): JPanel {
        return panel
    }
}