package abstracts

import pesca.tela.PanelInfo
import pesca.tela.StateEnum
import pescaLogic.StatePesca
import javax.swing.Timer

abstract class RotinaPesca(
    val statePesca: StateEnum
): Rotina {
    protected lateinit var rotinaPesca: Timer

    fun executaRotinaPesca(){
        PanelInfo.updateBotLogText(statePesca)
        executaRotina()
        rotinaPesca.start()
    }

    fun executaProximaRotina(){
        if (isLoop()) {
            finalizarLoopRotina()
        }

        StatePesca.gerenciaRotina(this.getProximaRotina())
    }

    fun finalizarLoopRotina() {
        if (rotinaPesca.isRunning) {
            rotinaPesca.stop()
        }
    }

    fun isLoop(): Boolean{
        return rotinaPesca.isRepeats
    }

    fun getAtualState(): StateEnum {
        return statePesca
    }
}