package pesca.rotina

import abstracts.RotinaPesca
import image.ImageUtils
import pesca.tela.PanelButton
import pesca.tela.PanelInfo
import pesca.tela.StateEnum
import pescaLogic.PuzzlePesca
import pescaLogic.ScreenUtils
import pescaLogic.SendInputUtils
import pescaLogic.StatePesca
import javax.swing.Timer

class ResolverPuzzle : RotinaPesca(StateEnum.RESOLVER_PUZZLE) {

    override fun executaRotina() {
        rotinaPesca = Timer(1000) {
            resolverPuzzle()
        }
        rotinaPesca.isRepeats = false
    }

    override fun getProximaRotina(): StateEnum {
        return StateEnum.ATACAR
    }

    private fun resolverPuzzle() {
        if (PuzzlePesca.hasPuzzle()) {
            PuzzlePesca.tocaAlarme()
            PanelButton.pauseBot()
        }else{
            executaProximaRotina()
        }
    }
}