package pesca.rotina

import abstracts.RotinaPesca
import pesca.tela.PanelCheckboxRotina
import pesca.tela.StateEnum
import pescaLogic.SendInputUtils
import javax.swing.Timer

class UsarRevive : RotinaPesca(StateEnum.USAR_REVIVE) {


    override fun executaRotina() {
        rotinaPesca = Timer(0) {
            usarRevive()
        }
        rotinaPesca.isRepeats = false
    }

    override fun getProximaRotina(): StateEnum {
        return StateEnum.PUXAR_ISCA
    }

    private fun usarRevive() {

        if(PanelCheckboxRotina.usarRevive()){
            SendInputUtils.usaRevive()
        }

        executaProximaRotina()
    }
}