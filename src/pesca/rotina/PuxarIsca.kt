package pesca.rotina

import abstracts.RotinaPesca
import image.ImageUtils
import pesca.tela.PanelInfo
import pesca.tela.StateEnum
import pescaLogic.PuzzlePesca
import pescaLogic.ScreenUtils
import pescaLogic.SendInputUtils
import javax.swing.Timer

class PuxarIsca : RotinaPesca(StateEnum.PUXAR_ISCA) {
    override fun executaRotina() {
        rotinaPesca = Timer(0) {
            puxarIsca()
        }
        rotinaPesca.isRepeats = false
    }

    override fun getProximaRotina(): StateEnum {
        return StateEnum.RESOLVER_PUZZLE
    }

    private fun puxarIsca() {

        SendInputUtils.puxaIsca()

        executaProximaRotina()
    }
}