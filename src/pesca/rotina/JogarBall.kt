package pesca.rotina

import abstracts.RotinaPesca
import image.ImageUtils
import pesca.tela.PanelCheckboxRotina
import pesca.tela.StateEnum
import pescaLogic.ScreenUtils
import javax.swing.Timer

class JogarBall : RotinaPesca(StateEnum.JOGAR_BALL) {
    override fun executaRotina() {
        rotinaPesca = Timer(0) {
            jogarBall()
        }
        rotinaPesca.isRepeats = false
    }

    override fun getProximaRotina(): StateEnum {
        return StateEnum.JOGAR_ISCA
    }

    private fun jogarBall() {
        if (PanelCheckboxRotina.jogarBall()) {
            // logica usar Revive
        }

        executaProximaRotina()
    }
}