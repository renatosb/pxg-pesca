package pescaLogic

import java.awt.Point
import java.awt.Rectangle

object SqmUtils {

    fun getSqmByCheckBox(indexCheckBox: Int, colunas: Int): Point{

        val deslocamento = -(colunas/2)
        val x = (indexCheckBox - 1) % colunas
        val y = (indexCheckBox - 1)/colunas
        val sqmCentral = ScreenUtils.getSqmScreenCenter()
        val sqmX = sqmCentral.x + (x + deslocamento) * ScreenUtils.getSqmDiameter()
        val sqmY = sqmCentral.y + (y + deslocamento) * ScreenUtils.getSqmDiameter()

        return Point(sqmX, sqmY)
    }

    fun getSqmRectangle(sqmCenter: Point): Rectangle {
        val sqmDiameter = ScreenUtils.getSqmDiameter()
        val sqmRadius = sqmDiameter / 2
        return Rectangle(
            sqmCenter.x - sqmRadius,
            sqmCenter.y - sqmRadius,
            sqmDiameter,
            sqmDiameter
        )
    }

    fun getBubblesSqm(sqmCenter: Point): Rectangle {
        val sqmDiameter = ScreenUtils.getSqmDiameter()
        val sqmRadius = sqmDiameter / 2
        return Rectangle(
            sqmCenter.x,
            sqmCenter.y - sqmRadius,
            sqmRadius,
            (sqmRadius *3)/4
        )
    }

    fun getPuzzleSqm(): Rectangle {
        return Rectangle(
            PuzzlePesca.getPuzzleInitialPosition().x,
            PuzzlePesca.getPuzzleInitialPosition().y,
            PuzzlePesca.getPuzzleFinalPosition().x - PuzzlePesca.getPuzzleInitialPosition().x,
            PuzzlePesca.getPuzzleFinalPosition().y - PuzzlePesca.getPuzzleInitialPosition().y
        )
    }
}