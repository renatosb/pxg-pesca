package pesca.rotina

import abstracts.RotinaPesca
import image.ImageUtils
import pesca.tela.StateEnum
import pescaLogic.ScreenUtils
import javax.swing.Timer

class AguardarPeixe : RotinaPesca(StateEnum.AGUARDAR_PEIXE) {
    override fun executaRotina() {
        rotinaPesca = Timer(300) {
            aguardarPeixe()
        }
    }

    override fun getProximaRotina(): StateEnum {
        return StateEnum.USAR_REVIVE
    }

    private fun aguardarPeixe() {
        if (ImageUtils.hasBubbles(ScreenUtils.getImageBubbles())) {
            executaProximaRotina()
        }
    }
}