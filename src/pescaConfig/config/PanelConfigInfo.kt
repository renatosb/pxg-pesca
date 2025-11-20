package pescaConfig.config

import abstracts.TelaPanel
import java.awt.GridLayout
import java.awt.Point
import javax.swing.JLabel
import javax.swing.JPanel

object PanelConfigInfo : TelaPanel {

    private val panel: JPanel = JPanel()
    private val firstPositionLabel: JLabel = JLabel("Posição Moeda Esquerda: ")
    private val secondPositionLabel: JLabel = JLabel("Posição Moeda Direita: ")
    private val sqmDiameterLabel: JLabel = JLabel("Raio SQM: ")
    private val sqmCenterLabel: JLabel = JLabel("SQM Centro da Tela: ")
    private val posiciaoInicialPuzzleLabel: JLabel = JLabel("Posição Inicial Puzzle: ")
    private val posiciaoFinalPuzzleLabel: JLabel = JLabel("Posição Final Puzzle: ")
    private val posicaoPrimeiroPokeLabel: JLabel = JLabel("Posição Primeiro Poke: ")
    private val listaPositions: List<JLabel> = listOf(
        firstPositionLabel,
        secondPositionLabel,
        sqmDiameterLabel,
        sqmCenterLabel,
        posiciaoInicialPuzzleLabel,
        posiciaoFinalPuzzleLabel,
        posicaoPrimeiroPokeLabel,
    )

    init {
        panel.layout = GridLayout(listaPositions.size, 1)
        listaPositions.map { panel.add(it) }
    }

    override fun getPanel(): JPanel {
        return panel
    }

    fun updateFirstPositionText(point: Point) {
        firstPositionLabel.text = "Posição Moeda Esquerda: ${point.x}, ${point.y}"
    }

    fun updateSecondPositionText(point: Point) {
        secondPositionLabel.text = "Posição Moeda Direita: ${point.x}, ${point.y}"
    }

    fun updateDiameterText(radius: Int) {
        sqmDiameterLabel.text = "Raio SQM: $radius"
    }

    fun updateSqmCenterText(point: Point) {
        sqmCenterLabel.text = "SQM Centro da Tela: ${point.x}, ${point.y}"
    }

    fun updateInitialPuzzlePositionText(point: Point) {
        posiciaoInicialPuzzleLabel.text = "Posição Inicial Puzzle: ${point.x}, ${point.y}"
    }

    fun updateFinalPuzzlePositionText(point: Point) {
        posiciaoFinalPuzzleLabel.text = "Posição Final Puzzle: ${point.x}, ${point.y}"
    }

    fun updatePosicaoPrimeiroPokeText(point: Point) {
        posicaoPrimeiroPokeLabel.text = "Posição Primeiro Poke: ${point.x}, ${point.y}"
    }
}