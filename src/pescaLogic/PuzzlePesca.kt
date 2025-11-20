package pescaLogic

import image.ConfigImages
import image.ImageUtils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import pescaConfig.config.PanelConfigInfo
import java.awt.Point
import java.awt.Rectangle
import java.awt.Robot
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.BufferedInputStream
import java.io.InputStream
import javax.sound.sampled.AudioSystem

object PuzzlePesca {

    private lateinit var puzzleInitialPosition: Point
    private lateinit var puzzleFinalPosition: Point
    private var robot: Robot = Robot()

    fun hasPuzzle(): Boolean {

        val puzzleRectangle: Rectangle = SqmUtils.getPuzzleSqm()
        val imagePuzzle = robot.createScreenCapture(puzzleRectangle)
        return ImageUtils.hasPuzzle(imagePuzzle)
    }

    fun exportPuzzleGrayImage() {
        val puzzleRectangle: Rectangle = SqmUtils.getPuzzleSqm()
        val imagePuzzle = robot.createScreenCapture(puzzleRectangle)
        ImageUtils.convertImageToGrayscale(imagePuzzle)
    }

    fun exportPuzzleImage() {
        val puzzleRectangle: Rectangle = SqmUtils.getPuzzleSqm()
        val imagePuzzle = robot.createScreenCapture(puzzleRectangle)
        ImageUtils.convertImageToFile(imagePuzzle)
    }

    fun tocaAlarme(){
        val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream("olha-o-puzzle.wav")
        val bufferedIn: InputStream = BufferedInputStream(inputStream)
        val clip = AudioSystem.getClip()
        clip.open(AudioSystem.getAudioInputStream(bufferedIn) )
        clip.start()
    }

    fun getPuzzleInitialPosition(): Point {
        return puzzleInitialPosition
    }

    fun getPuzzleFinalPosition(): Point {
        return puzzleFinalPosition
    }

    fun setPuzzleInitialPosition(point: Point) {
        puzzleInitialPosition = point
        PanelConfigInfo.updateInitialPuzzlePositionText(point)
    }

    fun setPuzzleFinalPosition(point: Point) {
        puzzleFinalPosition = point
        PanelConfigInfo.updateFinalPuzzlePositionText(point)
    }
}