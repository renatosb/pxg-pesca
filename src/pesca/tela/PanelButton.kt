package pesca.tela

import abstracts.TelaPanel
import image.ImageRecognition
import image.ImageUtils
import org.opencv.imgproc.Imgproc
import pesca.OpenCVLoader
import pescaLogic.PuzzleFish
import pescaLogic.PuzzlePesca
import pescaLogic.ScreenUtils
import pescaLogic.SendInputUtils
import pescaLogic.SqmUtils
import pescaLogic.StatePesca
import java.awt.Dimension
import java.awt.GraphicsEnvironment
import java.awt.GridLayout
import java.awt.Point
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.event.KeyEvent.VK_SPACE
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.math.min

object PanelButton : TelaPanel {

    private val panel: JPanel = JPanel()
    private val startPescaButton: JButton = JButton("|> Start |>")
    private var isRunning = false
    private val listaButtons: List<JButton> = listOf(
        startPescaButton,
    )

    init {
        panel.layout = GridLayout(listaButtons.size, 1)
        listaButtons.map { panel.add(it) }

//        startPescaButton.addActionListener {
//            if (!isRunning) {
//                startPescaButton.text = "|| Pause ||"
//                StatePesca.gerenciaRotina(StatePesca.getStateRotina().getAtualState())
//            } else {
//                pauseBot()
//            }
//            isRunning = !isRunning
//        }

        startPescaButton.addActionListener {
            OpenCVLoader
            val robot = Robot()
            //val capturaTela = robot.createScreenCapture(Rectangle(Toolkit.getDefaultToolkit().screenSize))
            //ImageUtils.convertImageToFile(capturaTela)


// 2) carregar template
            val templateFishMat = ImageRecognition.loadAllTemplatesFromResources()
            val timer = Timer(200) {
// 3) recortar puzzle
                val puzzleMat = PuzzleFish.bufferedImageToMat(ScreenUtils.getImagePuzzle())

// 4) identificar peixe
                val rect = ImageRecognition.identifyFishMultiTemplate(puzzleMat, templateFishMat)

                if (rect != null) {
                    val (cx, cy) = ImageRecognition.rectCenter(rect)
                    val telaX = PuzzlePesca.getPuzzleInitialPosition().x + cx
                    val telaY = PuzzlePesca.getPuzzleInitialPosition().y + cy

                    //println("Peixe localizado em coordenadas absolutas: $telaX, $telaY")

                    // aqui você faz ação no jogo
                    SendInputUtils.specialMouseMove(Point(telaX, telaY))
                }

            }
            timer.start()
        }
    }

    override fun getPanel(): JPanel {
        return panel
    }

    fun pauseBot() {
        startPescaButton.text = "|> Start |>"
        StatePesca.getStateRotina().finalizarLoopRotina()
        StatePesca.resetarRotina()
        isRunning = !isRunning
    }
}