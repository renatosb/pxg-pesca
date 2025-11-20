package pesca.rotina

import abstracts.RotinaPesca
import image.ImageUtils
import pesca.tela.PanelCheckboxRotina
import pesca.tela.PanelInfo
import pesca.tela.StateEnum
import pescaLogic.PuzzlePesca
import pescaLogic.ScreenUtils
import pescaLogic.SendInputUtils
import java.awt.event.KeyEvent.VK_E
import javax.swing.Timer

class ColetarLoot : RotinaPesca(StateEnum.COLETAR_LOOT) {
    override fun executaRotina() {
        rotinaPesca = Timer(0) {
            coletar()
        }
        rotinaPesca.isRepeats = false
    }

    override fun getProximaRotina(): StateEnum {
        return StateEnum.JOGAR_BALL
    }

    private fun coletar() {

        if (PanelCheckboxRotina.coletarLoot()) {
            SendInputUtils.coletaLoot()
        }

        executaProximaRotina()
    }
}