package image

import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

// ====================

object ImageRecognition {

    // ============================================================================
// CONVERSÃO ENTRE BufferedImage ↔ Mat
// ============================================================================
    fun bufferedImageToMat(image: BufferedImage): Mat {
        val width = image.width
        val height = image.height
        val mat: Mat

        when (image.type) {
            BufferedImage.TYPE_3BYTE_BGR -> {
                mat = Mat(height, width, CvType.CV_8UC3)
                val data = (image.raster.dataBuffer as java.awt.image.DataBufferByte).data
                mat.put(0, 0, data)
            }

            BufferedImage.TYPE_BYTE_GRAY -> {
                mat = Mat(height, width, CvType.CV_8UC1)
                val data = (image.raster.dataBuffer as java.awt.image.DataBufferByte).data
                mat.put(0, 0, data)
            }

            else -> {
                val converted = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)
                val g = converted.createGraphics()
                g.drawImage(image, 0, 0, null)
                g.dispose()
                return bufferedImageToMat(converted)
            }
        }

        return mat
    }

    // ============================================================================
// CARREGAR TEMPLATE FINAL VIA ImageIO
// ============================================================================
    fun loadAllTemplatesFromResources(): List<Mat> {
        val url = object {}.javaClass.classLoader.getResource("pesca")
            ?: throw IllegalStateException("Diretório pesca/ não encontrado em resources")

        val folder = File(url.toURI())
        val list = mutableListOf<Mat>()

        folder.listFiles()?.forEach { f ->
            val name = f.name.lowercase()
            if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")) {
                // Usar ImageIO para abrir
                val bf = ImageIO.read(f)
                if (bf != null) {
                    val mat = bufferedImageToMat(bf)
                    list.add(mat)
                }
            }
        }

        if (list.isEmpty()) {
            throw IllegalStateException("Nenhum template encontrado no diretório resources/pesca/")
        }

        return list
    }

    // ============================================================================
// PREPROCESSAMENTO DO PUZZLE
// ============================================================================
    fun preprocessForDetection(src: Mat): Mat {
        val gray = Mat()
        val blur = Mat()
        val clahe = Imgproc.createCLAHE(2.0, Size(8.0, 8.0))
        val eq = Mat()

        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY)
        Imgproc.GaussianBlur(gray, blur, Size(5.0, 5.0), 0.0)
        clahe.apply(blur, eq)

        return eq
    }

    // ============================================================================
// DETECTAR CANDIDATOS AO PEIXE
// ============================================================================
    fun findFishCandidates(preprocessed: Mat): List<Rect> {
        val edges = Mat()
        Imgproc.Canny(preprocessed, edges, 40.0, 120.0)

        val contours = ArrayList<MatOfPoint>()
        Imgproc.findContours(edges, contours, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)

        val list = mutableListOf<Rect>()

        for (c in contours) {
            val area = Imgproc.contourArea(c)
            if (area < 180.0) continue

            val r = Imgproc.boundingRect(c)
            val aspect = r.width.toDouble() / r.height.toDouble()
            if (aspect !in 0.5..4.0) continue

            list.add(r)
        }

        return list
    }

    // ============================================================================
// MATCH SHAPE DO TEMPLATE
// ============================================================================
    fun matchFishTemplate(
        template: Mat,
        puzzle: Mat,
        candidates: List<Rect>,
        threshold: Double = 0.60
    ): Rect? {

        // ----- PREPARA TEMPLATE -----
        val tplGray = Mat()
        Imgproc.cvtColor(template, tplGray, Imgproc.COLOR_BGR2GRAY)
        Imgproc.threshold(tplGray, tplGray, 0.0, 255.0, Imgproc.THRESH_OTSU)

        val tplContours = ArrayList<MatOfPoint>()
        Imgproc.findContours(tplGray.clone(), tplContours, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)
        if (tplContours.isEmpty()) return null

        val tplCnt = tplContours.maxByOrNull { Imgproc.contourArea(it) }!!

        // ----- PROCURAR MELHOR CANDIDATO -----
        var bestScore = Double.MAX_VALUE
        var bestRect: Rect? = null

        for (r in candidates) {

            val roi = Mat(puzzle, r)
            val roiGray = Mat()
            Imgproc.cvtColor(roi, roiGray, Imgproc.COLOR_BGR2GRAY)
            Imgproc.threshold(roiGray, roiGray, 0.0, 255.0, Imgproc.THRESH_OTSU)

            val roiContours = ArrayList<MatOfPoint>()
            Imgproc.findContours(roiGray.clone(), roiContours, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)
            if (roiContours.isEmpty()) continue

            val roiCnt = roiContours.maxByOrNull { Imgproc.contourArea(it) }!!

            val score = Imgproc.matchShapes(
                tplCnt, roiCnt,
                Imgproc.CONTOURS_MATCH_I1,
                0.0
            )

            if (score < bestScore) {
                bestScore = score
                bestRect = r
            }
        }

        return if (bestScore < threshold) bestRect else null
    }

    // ============================================================================
// IDENTIFICAR O PEIXE NO PUZZLE
// ============================================================================
    fun identifyFishMultiTemplate(puzzleMat: Mat, templates: List<Mat>): Rect? {
        val prep = preprocessForDetection(puzzleMat)
        val candidates = findFishCandidates(prep)
        if (candidates.isEmpty()) return null

        var bestRect: Rect? = null
        var bestScore = Double.MAX_VALUE

        for (tpl in templates) {
            val r = matchFishTemplate(tpl, puzzleMat, candidates, threshold = 0.60)
            if (r != null) {
                // recompute score using matchShapes for ranking
                val roi = Mat(puzzleMat, r)
                val score = Imgproc.matchShapes(
                    tpl, roi,
                    Imgproc.CONTOURS_MATCH_I1, 0.0
                )

                if (score < bestScore) {
                    bestScore = score
                    bestRect = r
                }
            }
        }

        return bestRect
    }

    // ============================================================================
// CENTRO DO RECT
// ============================================================================
    fun rectCenter(r: Rect): Pair<Int, Int> {
        return Pair(r.x + r.width / 2, r.y + r.height / 2)
    }

}