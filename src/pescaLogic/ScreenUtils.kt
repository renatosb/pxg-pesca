package pescaLogic

import image.ImageUtils
import pesca.tela.PanelButton
import pesca.tela.PanelCheckbox
import pescaConfig.tela.PanelInfo
import pescaConfig.config.PanelConfigInfo
import java.awt.Point
import java.awt.Rectangle
import java.awt.Robot
import java.awt.image.BufferedImage

object ScreenUtils {

    private lateinit var mouseFirstPointer: Point
    private lateinit var mouseSecondPointer: Point
    private var sqmDiameter: Int = 0
    private lateinit var sqmScreenCenter: Point
    private val robot = Robot()

    fun calculateDiameter() {
        sqmDiameter = (mouseSecondPointer.x - mouseFirstPointer.x)
        PanelConfigInfo.updateDiameterText(sqmDiameter)
    }

    fun getSqmDiameter(): Int {
        return sqmDiameter
    }

    fun calcScreenCenter() {
        sqmScreenCenter = Point(mouseSecondPointer.x, mouseSecondPointer.y - sqmDiameter)
        PanelInfo.updateCenterPositionText(sqmScreenCenter)
        pesca.tela.PanelInfo.updateCenterPositionText(sqmScreenCenter)
        PanelConfigInfo.updateSqmCenterText(sqmScreenCenter)
    }

    fun getSqmScreenCenter(): Point {
        return sqmScreenCenter
    }

    fun setMouseFirstPointer(point: Point) {
        mouseFirstPointer = point
        PanelConfigInfo.updateFirstPositionText(point)
    }

    fun setMouseSecondPointer(point: Point) {
        mouseSecondPointer = point
        PanelConfigInfo.updateSecondPositionText(point)
    }

    fun getImageBubbles(): BufferedImage {
        val sqmRectangle: Rectangle = SqmUtils.getBubblesSqm(PanelCheckbox.getSqmPesca())
        return ImageUtils.returnImageToGrayscale(robot.createScreenCapture(sqmRectangle))
    }

    fun getStringConfigs(): String {
        return "mouseFirstPointer: ${mouseFirstPointer.x}, ${mouseFirstPointer.y}\n" +
                "mouseSecondPointer: ${mouseSecondPointer.x}, ${mouseSecondPointer.y}\n" +
                "posicaoInicialPuzzle: ${PuzzlePesca.getPuzzleInitialPosition().x}, ${PuzzlePesca.getPuzzleInitialPosition().y}\n" +
                "posicaoFinalPuzzle: ${PuzzlePesca.getPuzzleFinalPosition().x}, ${PuzzlePesca.getPuzzleFinalPosition().y}\n" +
                "posicaoPrimeiroPoke: ${SendInputUtils.getPosicacaoPrimeiroPoke().x}, ${SendInputUtils.getPosicacaoPrimeiroPoke().y}\n"
    }
}