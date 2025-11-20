package pescaLogic


import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfInt
import org.opencv.core.MatOfPoint
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import java.awt.Point
import java.awt.Rectangle
import java.awt.image.BufferedImage
import kotlin.math.abs

object PuzzleFish {

    // -------------------------------------------------------------------------
    // 1) Converte BufferedImage → Mat (universal)
    // -------------------------------------------------------------------------
    fun bufferedImageToMat(bi: BufferedImage): Mat {
        val mat = Mat(bi.height, bi.width, CvType.CV_8UC3)
        val data = ByteArray(bi.width * bi.height * 3)

        var index = 0
        for (y in 0 until bi.height) {
            for (x in 0 until bi.width) {
                val rgb = bi.getRGB(x, y)
                data[index++] = (rgb and 0xFF).toByte()          // B
                data[index++] = ((rgb shr 8) and 0xFF).toByte()  // G
                data[index++] = ((rgb shr 16) and 0xFF).toByte() // R
            }
        }
        mat.put(0, 0, data)
        return mat
    }


    // -------------------------------------------------------------------------
    // 2) Localiza automaticamente a barra preta (X,Y do puzzle)
    // -------------------------------------------------------------------------
    fun findFishingBarPointInRegion(regionImg: BufferedImage, regionRect: Rectangle): Point? {
        val w = regionImg.width
        val h = regionImg.height

        // segurança: se imagem inválida
        if (w <= 0 || h <= 0) return null

        val darkRatio = DoubleArray(w)

        // 1) Varrer cada coluna local (da região) calculando proporção de pixels escuros
        for (x in 0 until w) {
            var dark = 0
            for (y in 0 until h) {
                val rgb = regionImg.getRGB(x, y)
                val r = (rgb shr 16) and 0xFF
                val g = (rgb shr 8) and 0xFF
                val b = rgb and 0xFF
                val bright = (r + g + b) / 3
                if (bright < 60) dark++
            }
            darkRatio[x] = dark.toDouble() / h.toDouble()
        }

        // 2) Encontrar sequência de colunas escuras consecutivas (a barra)
        var start = -1
        var end = -1
        for (x in 0 until w) {
            if (darkRatio[x] > 0.45) {
                if (start == -1) start = x
                end = x
            } else {
                if (start != -1) break
            }
        }

        if (start == -1 || end == -1) return null

        val width = end - start + 1

        // validações adicionais (tamanho consistente com barra)
        // Note: os valores abaixo podem ser ajustados se sua barra tiver outra largura
        if (width < 10 || width > 120) return null

        val avgDark = (start..end).map { darkRatio[it] }.average()
        if (avgDark < 0.45) return null

        // evitar "spurious" de apenas 1-2 colunas
        if ((end - start) < 6) return null

        // Se tudo OK, computa centro da barra **em coordenadas absolutas da tela**
        val barCenterXLocal = (start + end) / 2
        val barCenterYLocal = h / 2  // assumimos barra vertical que percorre a altura da região

        val absX = regionRect.x + barCenterXLocal
        val absY = regionRect.y + barCenterYLocal

        return Point(absX, absY)
    }

    // -------------------------------------------------------------------------
    // 3) Detectar o PEIXE (qualquer cor) pelo formato
    // -------------------------------------------------------------------------
    fun detectFishShape(bar: BufferedImage): Rect? {
        val mat = bufferedImageToMat(bar)

        // Gray
        val gray = Mat()
        Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY)

        // Contraste
        val clahe = Imgproc.createCLAHE(3.5, Size(8.0, 8.0))
        clahe.apply(gray, gray)

        // Bordas
        val edges = Mat()
        Imgproc.Canny(gray, edges, 30.0, 130.0)

        // Fechar contornos
        val kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, Size(5.0, 5.0))
        Imgproc.morphologyEx(edges, edges, Imgproc.MORPH_CLOSE, kernel)

        // Contornos
        val contours = ArrayList<MatOfPoint>()
        Imgproc.findContours(edges, contours, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)

        var bestRect: Rect? = null
        var bestScore = Double.NEGATIVE_INFINITY

        for (c in contours) {
            val rect = Imgproc.boundingRect(c)
            val w = rect.width.toDouble()
            val h = rect.height.toDouble()
            val area = w * h

            if (w < 20 || w > 60) continue
            if (h < 10 || h > 40) continue
            if (area !in 300.0..2000.0) continue

            val aspect = w / h
            if (aspect !in 1.4..2.4) continue

            // Convex hull (forma do peixe com cauda)
            val hullIdx = MatOfInt()
            Imgproc.convexHull(c, hullIdx)

            val pts = c.toArray()
            val hullPts = hullIdx.toArray().map { idx -> pts[idx] }.toTypedArray()
            val hullMat = MatOfPoint(*hullPts)

            val contourArea = Imgproc.contourArea(c)
            val hullArea = Imgproc.contourArea(hullMat)
            if (hullArea <= 0) continue

            val solidity = contourArea / hullArea
            if (solidity !in 0.68..0.86) continue

            // Score de forma
            val score =
                (area) *
                        (1.0 - abs(aspect - 1.9)) *
                        (1.0 - abs(solidity - 0.77))

            if (score > bestScore) {
                bestScore = score
                bestRect = rect
            }
        }

        return bestRect
    }


    // -------------------------------------------------------------------------
    // 4) Detectar a BARRA AZUL (subida quando aperta espaço)
    // -------------------------------------------------------------------------
    fun detectBlueBar(bar: BufferedImage): Rect? {
        val mat = bufferedImageToMat(bar)

        val hsv = Mat()
        Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV)

        // Azul do puzzle: H 90-125
        val lower = Scalar(90.0, 80.0, 80.0)
        val upper = Scalar(130.0, 255.0, 255.0)

        val mask = Mat()
        Core.inRange(hsv, lower, upper, mask)

        // contornos
        val contours = ArrayList<MatOfPoint>()
        Imgproc.findContours(mask, contours, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)

        var best: Rect? = null
        var bestArea = 0.0

        for (c in contours) {
            val r = Imgproc.boundingRect(c)
            val area = r.width * r.height
            if (area > bestArea) {
                bestArea = area.toDouble()
                best = r
            }
        }

        return best
    }


    // -------------------------------------------------------------------------
    // 5) Estrutura de retorno do puzzle
    // -------------------------------------------------------------------------
    data class FishingResult(
        val fishRect: Rect?,
        val blueRect: Rect?,
        val shouldPressSpace: Boolean
    )


    // -------------------------------------------------------------------------
    // 6) Lógica de decisão: deve apertar espaço?
    // -------------------------------------------------------------------------
    fun solveLogic(bar: BufferedImage): FishingResult {

        val fish = detectFishShape(bar)
        val blue = detectBlueBar(bar)

        if (fish == null || blue == null) {
            return FishingResult(null, null, false)
        }

        val fishY = fish.y + fish.height / 2
        val blueY = blue.y + blue.height

        val shouldPress = blueY < fishY

        return FishingResult(fish, blue, shouldPress)
    }
}
