package image

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO

object ImageUtils {

    fun convertImageToGrayscale(originalImage: BufferedImage, outputFilePath: String = "gray_image.png") {
        val outputFile = File(outputFilePath)
        val width = originalImage.width
        val height = originalImage.height

        val grayscaleImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)

        for (i in 0 until height) {
            for (j in 0 until width) {
                // Obtém o valor do pixel (cor)
                val color = originalImage.getRGB(j, i)
                // TYPE_BYTE_GRAY já faz o cálculo e define o pixel automaticamente
                grayscaleImage.setRGB(j, i, color)
            }
        }

        ImageIO.write(grayscaleImage, "png", outputFile)
        println("Imagem convertida para escala de cinza e salva em ${outputFile.absolutePath}")
    }

    fun convertImageToFile(originalImage: BufferedImage, outputFilePath: String = "image.png") {
        val outputFile = File(outputFilePath)
        ImageIO.write(originalImage, "png", outputFile)
        println("Imagem convertida para escala de cinza e salva em ${outputFile.absolutePath}")
    }

    fun convertManyImagesToGrayscale(numImgs: Int) {
        for(index in 1..numImgs)
            convertImageToGrayscale(loadImageFromResources(
                "pesca/${index}.PNG"),
                "gray_images/${index}_gray.png")
    }

    fun returnImageToGrayscale(originalImage: BufferedImage): BufferedImage {
        val width = originalImage.width
        val height = originalImage.height

        val grayscaleImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)

        for (i in 0 until height) {
            for (j in 0 until width) {
                // Obtém o valor do pixel (cor)
                val color = originalImage.getRGB(j, i)
                // TYPE_BYTE_GRAY já faz o cálculo e define o pixel automaticamente
                grayscaleImage.setRGB(j, i, color)
            }
        }

        return grayscaleImage
    }

    fun hasBubbles(imageBubbles: BufferedImage): Boolean {
        val width = imageBubbles.width
        val height = imageBubbles.height

        for (i in 0 until height) {
            for (j in 0 until width) {

                val color = Color(imageBubbles.getRGB(j, i))

                if (((color.red > 110) && (color.red <= 119))
                    && ((color.blue > 110) && (color.blue < 119))
                    && ((color.green > 110) && (color.green < 119))
                ) {
                    return true
                }
            }
        }

        return false
    }

    fun hasPuzzle(imageBubbles: BufferedImage): Boolean {
        val width = imageBubbles.width
        val height = imageBubbles.height

        for (i in 0 until height) {
            for (j in 0 until width) {

                val color = Color(imageBubbles.getRGB(j, i))

                if (((color.red > 20) && (color.red <= 29))
                    && ((color.blue > 20) && (color.blue < 29))
                    && ((color.green > 24) && (color.green < 33))
                ) {
                    return true
                }
            }
        }

        return false
    }

    fun loadImageFromResources(imagePath: String): BufferedImage {
        val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream(imagePath)
        return inputStream.let { ImageIO.read(it) }
    }
}