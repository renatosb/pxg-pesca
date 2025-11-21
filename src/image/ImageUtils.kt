package image

import org.opencv.core.CvType
import org.opencv.core.Mat
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

    fun bufferedToMat(bi: BufferedImage): Mat {
        // Garantir que o buffer esteja no formato 3BYTE_BGR (o mais aceito pelo OpenCV)
        val imgBGR: BufferedImage =
            if (bi.type != BufferedImage.TYPE_3BYTE_BGR) {
                val newBi = BufferedImage(bi.width, bi.height, BufferedImage.TYPE_3BYTE_BGR)
                val g = newBi.createGraphics()
                g.drawImage(bi, 0, 0, null)
                g.dispose()
                newBi
            } else bi

        // Extrair bytes brutos da imagem
        val raster = imgBGR.raster
        val dataBuffer = raster.dataBuffer as java.awt.image.DataBufferByte
        val data = dataBuffer.data

        // Criar Mat correspondente
        val mat = Mat(imgBGR.height, imgBGR.width, CvType.CV_8UC3)
        mat.put(0, 0, data)

        return mat
    }

    fun matToBufferedImage(mat: Mat): BufferedImage {
        val width = mat.width()
        val height = mat.height()
        val channels = mat.channels()

        val buffer = ByteArray(width * height * channels)
        mat.get(0, 0, buffer)

        val type = when (channels) {
            1 -> BufferedImage.TYPE_BYTE_GRAY
            3 -> BufferedImage.TYPE_3BYTE_BGR
            else -> throw IllegalArgumentException("Can’t convert Mat with $channels channels")
        }

        val image = BufferedImage(width, height, type)
        image.raster.setDataElements(0, 0, width, height, buffer)

        return image
    }

    fun to3ByteBGR(src: BufferedImage): BufferedImage {
        if (src.type == BufferedImage.TYPE_3BYTE_BGR) {
            return src
        }

        val dst = BufferedImage(src.width, src.height, BufferedImage.TYPE_3BYTE_BGR)
        val g = dst.createGraphics()
        g.drawImage(src, 0, 0, null)
        g.dispose()
        return dst
    }

    fun loadImageFromResources(imagePath: String): BufferedImage {
        val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream(imagePath)
        return inputStream.let { ImageIO.read(it) }
    }
}