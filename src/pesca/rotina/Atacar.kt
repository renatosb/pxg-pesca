package pesca.rotina

import abstracts.RotinaPesca
import image.ImageUtils
import pesca.tela.PanelCheckboxRotina
import pesca.tela.PanelInfo
import pesca.tela.StateEnum
import pescaLogic.PuzzlePesca
import pescaLogic.ScreenUtils
import pescaLogic.SendInputUtils
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_1
import java.awt.event.KeyEvent.VK_2
import java.awt.event.KeyEvent.VK_3
import java.awt.event.KeyEvent.VK_4
import java.awt.event.KeyEvent.VK_5
import java.awt.event.KeyEvent.VK_6
import java.awt.event.KeyEvent.VK_7
import java.awt.event.KeyEvent.VK_8
import java.awt.event.KeyEvent.VK_9
import javax.swing.Timer

class Atacar : RotinaPesca(StateEnum.ATACAR) {
    override fun executaRotina() {
        rotinaPesca = Timer(0) {
            atacar()
        }
        rotinaPesca.isRepeats = false
    }

    override fun getProximaRotina(): StateEnum {
        return StateEnum.COLETAR_LOOT
    }

    private fun atacar() {
        if (PanelCheckboxRotina.atacar()) {
            SendInputUtils.clicaCentroTela()
            val numSelected = PanelCheckboxRotina.getNumAtaqueSelected()
            for (index in 1..numSelected) {
                SendInputUtils.usaGolpe(getKeyNumberByInt(index))
            }
        }

        executaProximaRotina()
    }

    fun getKeyNumberByInt(number: Int): Int {
        when (number) {
            1 -> return VK_1
            2 -> return VK_2
            3 -> return VK_3
            4 -> return VK_4
            5 -> return VK_5
            6 -> return VK_6
            7 -> return VK_7
            8 -> return VK_8
            9 -> return VK_9

            else -> return 0
        }
    }
}