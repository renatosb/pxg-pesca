package pescaConfig.config

import fileManager.ConfigIni
import pescaLogic.ScreenUtils
import abstracts.TelaPanel
import pescaConfig.config.image.TelaImageExample
import pescaLogic.PuzzlePesca
import pescaLogic.SendInputUtils
import java.awt.GridLayout
import java.awt.MouseInfo
import java.awt.Point
import javax.swing.JButton
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.Timer

object PanelConfigButton : TelaPanel {

    private val panel: JPanel = JPanel()
    private val tutorialConfigButton: JButton = JButton("TUTORIAL")
    private val mouseFirstPointerButton: JButton = JButton("Moeda Esquerda(delay 3s)")
    private val mouseSecondPointerButton: JButton = JButton("Moeda Direita(delay 3s)")
    private val calcRadiusButton: JButton = JButton("Calcular Raio SQM")
    private val calcScreenCenterButton: JButton = JButton("Calcular SQM do Centro da Tela")
    private val posiciaoInicialPuzzleButton: JButton = JButton("Posição Inicial Puzzle")
    private val posiciaoFinalPuzzleButton: JButton = JButton("Posição Final Puzzle")
    private val posicaPrimeiroPoke: JButton = JButton("Posição Primeiro Pokemon")
    private val salvarConfigsButton: JButton = JButton("SALVAR CONFIGS")
    private val listaButtons: List<JButton> = listOf(
        tutorialConfigButton,
        mouseFirstPointerButton,
        mouseSecondPointerButton,
        calcRadiusButton,
        calcScreenCenterButton,
        posiciaoInicialPuzzleButton,
        posiciaoFinalPuzzleButton,
        posicaPrimeiroPoke,
        salvarConfigsButton,
    )

    init {
        panel.layout = GridLayout(listaButtons.size, 1)
        listaButtons.map { panel.add(it) }

        tutorialConfigButton.addActionListener {
            TelaImageExample.isVisible = true
        }

        mouseFirstPointerButton.addActionListener {
            val delay = Timer(3000) {
                val mousePoint = getMousePointer()
                ScreenUtils.setMouseFirstPointer(mousePoint)
            }
            delay.isRepeats = false
            delay.start()
        }

        mouseSecondPointerButton.addActionListener {
            val delay = Timer(3000) {
                val mousePoint = getMousePointer()
                PanelConfigInfo.updateSecondPositionText(mousePoint)
                ScreenUtils.setMouseSecondPointer(mousePoint)
            }
            delay.isRepeats = false
            delay.start()
        }

        calcRadiusButton.addActionListener {
            try {
                ScreenUtils.calculateDiameter()
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(
                    panel,
                    "Erro ao calcular o raio SQM. Certifique-se de ter definido as posicões de moeda corretamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }

        calcScreenCenterButton.addActionListener {
            try {
                ScreenUtils.calcScreenCenter()
            } catch (e: UninitializedPropertyAccessException) {
                JOptionPane.showMessageDialog(
                    panel,
                    "Erro ao calcular a posiçao do centro da tela. Certifique-se de ter definido as posicões de moeda e valor do raio corretamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }

        posiciaoInicialPuzzleButton.addActionListener {
            val delay = Timer(3000) {
                val mousePoint = getMousePointer()
                PuzzlePesca.setPuzzleInitialPosition(mousePoint)
            }
            delay.isRepeats = false
            delay.start()
        }

        posiciaoFinalPuzzleButton.addActionListener {
            val delay = Timer(3000) {
                val mousePoint = getMousePointer()
                PuzzlePesca.setPuzzleFinalPosition(mousePoint)
            }
            delay.isRepeats = false
            delay.start()
        }

        posicaPrimeiroPoke.addActionListener{
            val delay = Timer(3000) {
                val mousePoint = getMousePointer()
                SendInputUtils.setPosiciaoPrimeiroPoke(mousePoint)
            }
            delay.isRepeats = false
            delay.start()
        }

        salvarConfigsButton.addActionListener {
            val configsString = ScreenUtils.getStringConfigs()
            ConfigIni.createTxtFile(configsString)
        }
    }

    override fun getPanel(): JPanel {
        return panel
    }

    fun getMousePointer(): Point {
        return MouseInfo.getPointerInfo().location
    }
}