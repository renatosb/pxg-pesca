package pescaLogic

import pescaConfig.config.PanelConfigInfo
import java.awt.MouseInfo
import java.awt.Point
import java.awt.Robot
import java.awt.event.KeyEvent.*
import java.awt.event.MouseEvent

object SendInputUtils {

    private val robot: Robot = Robot()
    private val keyDelay = 100
    private val mouseMoveDelay = 300
    private val delayPuxaIsca = 1000
    private val delayGolpe = 700
    private val delaySoltaPoke = 500
    private val delayColeta = 1000

    private lateinit var posicaoPrimeiroPoke: Point

    fun jogaIsca(sqmPesca: Point) {
        val mouseInitialpoint = MouseInfo.getPointerInfo().location

        mouseMove(sqmPesca)
        leftClickMouse()
        pressKey(VK_G)
        leftClickMouse()

        mouseMove(mouseInitialpoint)
    }

    fun puxaIsca() {
        pressKey(VK_G)
        robot.delay(delayPuxaIsca)
    }

    fun clicaCentroTela() {
        val mouseInitialpoint = MouseInfo.getPointerInfo().location
        mouseMove(ScreenUtils.getSqmScreenCenter())
        leftClickMouse()
        mouseMove(mouseInitialpoint)
    }

    fun usaRevive(){
        val mouseInitialpoint = MouseInfo.getPointerInfo().location

        mouseMove(posicaoPrimeiroPoke)
        leftClickMouse()
        pressKey(VK_X)
        leftClickMouse()
        pokeStop()

        mouseMove(mouseInitialpoint)
    }

    fun pokeStop(){
        robot.delay(delaySoltaPoke)
        robot.keyPress(VK_SHIFT)
        robot.keyPress(VK_S)
        robot.delay(keyDelay)
        robot.keyRelease(VK_S)
        robot.keyRelease(VK_SHIFT)
    }

    fun pressKey(key: Int) {
        robot.keyPress(key)
        robot.delay(keyDelay)
        robot.keyRelease(key)
    }

    fun usaGolpe(key: Int) {
        robot.keyPress(key)
        robot.delay(keyDelay)
        robot.keyRelease(key)
        robot.delay(delayGolpe)
    }

    fun coletaLoot(){
        robot.delay(delayColeta)
        pressKey(VK_E)
    }

    fun leftClickMouse() {
        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK)
        robot.delay(50)
        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK)
    }

    fun mouseMove(point: Point?) {
        if(point != null) {
            robot.mouseMove(point.x, point.y)
            robot.delay(mouseMoveDelay)
        }
    }

    fun specialMouseMove(point: Point?) {
        if(point != null) {
            robot.mouseMove(point.x, point.y)
        }
    }

    fun setPosiciaoPrimeiroPoke(point: Point) {
        posicaoPrimeiroPoke = point
        PanelConfigInfo.updatePosicaoPrimeiroPokeText(point)
    }

    fun getPosicacaoPrimeiroPoke(): Point {
        return posicaoPrimeiroPoke
    }
}