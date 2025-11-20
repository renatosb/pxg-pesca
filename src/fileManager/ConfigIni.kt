package fileManager

import pescaLogic.ScreenUtils
import pescaConfig.tela.Tela
import pescaLogic.PuzzlePesca
import pescaLogic.SendInputUtils
import java.awt.Point
import java.io.File
import javax.swing.JOptionPane

object ConfigIni {
    private val path = "configIni.txt"

    fun createTxtFile(content: String) {
        try {
            val file = File(path)
            file.writeText(content)
            JOptionPane.showMessageDialog(Tela, "Arquivo criado em: $path", "Sucesso", JOptionPane.INFORMATION_MESSAGE)
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(
                Tela,
                "Erro ao criar arquivo: ${e.message}",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }

    fun loadConfigs() {
        val file = File(path)
        if (file.exists()) {
            val lines = file.readLines()
            for (line in lines) {
                val parts = line.split(":")
                if (parts.size == 2) {
                    val key = parts[0].trim()
                    val valueParts = parts[1].trim().split(",")
                    when (key) {
                        "mouseFirstPointer" -> {
                            ScreenUtils.setMouseFirstPointer(Point(valueParts[0].trim().toInt(), valueParts[1].trim().toInt()))
                        }
                        "mouseSecondPointer" -> {
                             ScreenUtils.setMouseSecondPointer(Point(valueParts[0].trim().toInt(), valueParts[1].trim().toInt()))
                        }
                        "posicaoInicialPuzzle" -> {
                            PuzzlePesca.setPuzzleInitialPosition(Point(valueParts[0].trim().toInt(), valueParts[1].trim().toInt()))
                        }
                        "posicaoFinalPuzzle" -> {
                            PuzzlePesca.setPuzzleFinalPosition(Point(valueParts[0].trim().toInt(), valueParts[1].trim().toInt()))
                        }
                        "posicaoPrimeiroPoke" -> {
                            SendInputUtils.setPosiciaoPrimeiroPoke(Point(valueParts[0].trim().toInt(), valueParts[1].trim().toInt()))
                        }
                    }
                }
            }

            ScreenUtils.calculateDiameter()
            ScreenUtils.calcScreenCenter()
        }
    }
}