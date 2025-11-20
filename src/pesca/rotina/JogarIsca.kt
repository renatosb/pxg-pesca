package pesca.rotina

import abstracts.RotinaPesca
import pesca.tela.PanelCheckbox
import pesca.tela.StateEnum
import pescaLogic.SendInputUtils
import javax.swing.Timer

class JogarIsca : RotinaPesca(StateEnum.JOGAR_ISCA) {
    override fun executaRotina() {
        rotinaPesca = Timer(0) {
            jogarIsca()
        }
        rotinaPesca.isRepeats = false
    }

    override fun getProximaRotina(): StateEnum {
        return StateEnum.AGUARDAR_PEIXE
    }

    private fun jogarIsca() {

        SendInputUtils.jogaIsca(PanelCheckbox.getSqmPesca())

        executaProximaRotina()
    }
}