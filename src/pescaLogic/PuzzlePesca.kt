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
        val img: BufferedImage = ImageUtils.to3ByteBGR(ScreenUtils.getImagePuzzle())

        val width = img.width
        val height = img.height

        val data = (img.raster.dataBuffer as DataBufferByte).data

        // A barra preta tem média ~40–55 (confirmado no print)
        val darkThreshold = 60

        // amostragem: testa 1 coluna a cada 5
        // (a barra é larga o suficiente para sempre ser capturada)
        val step = maxOf(1, width / 30)  // adaptativo, ~30 colunas amostradas

        for (x in 0 until width step step) {
            var sum = 0

            var offset = x * 3   // offset inicial na primeira linha

            for (y in 0 until height) {
                // cada linha avança width*3 bytes
                val base = offset + y * width * 3

                val b = data[base].toInt() and 0xFF
                val g = data[base + 1].toInt() and 0xFF
                val r = data[base + 2].toInt() and 0xFF

                sum += r + g + b
            }

            // média da coluna
            val colAvg = sum / (height * 3.0)

            // encontrou a barra preta
            if (colAvg < darkThreshold) {
                return true
            }
        }

        return false
    }


    fun exportPuzzleGrayImage() {
        ImageUtils.convertImageToGrayscale(ScreenUtils.getImagePuzzle())
    }

    fun exportPuzzleImage() {
        ImageUtils.convertImageToFile(ScreenUtils.getImagePuzzle())
    }

    fun tocaAlarme() {
        val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream("olha-o-puzzle.wav")
        val bufferedIn: InputStream = BufferedInputStream(inputStream)
        val clip = AudioSystem.getClip()
        clip.open(AudioSystem.getAudioInputStream(bufferedIn))
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