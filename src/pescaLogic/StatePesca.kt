package pescaLogic

import abstracts.RotinaPesca
import pesca.rotina.*
import pesca.tela.StateEnum


object StatePesca {

    private var stateRotina: RotinaPesca = JogarIsca()

    fun gerenciaRotina(
        stateEnum: StateEnum,
    ) {
        when (stateEnum) {
            StateEnum.JOGAR_ISCA -> {
                stateRotina = JogarIsca()
            }

            StateEnum.AGUARDAR_PEIXE -> {
                stateRotina = AguardarPeixe()
            }

            StateEnum.PUXAR_ISCA -> {
                stateRotina = PuxarIsca()
            }

            StateEnum.RESOLVER_PUZZLE -> {
                stateRotina = ResolverPuzzle()
            }

            StateEnum.USAR_REVIVE -> {
                stateRotina = UsarRevive()
            }

            StateEnum.ATACAR -> {
                stateRotina = Atacar()
            }

            StateEnum.COLETAR_LOOT -> {
                stateRotina = ColetarLoot()
            }

            StateEnum.JOGAR_BALL -> {
                stateRotina = JogarBall()
            }
        }

        stateRotina.executaRotinaPesca()
    }

    fun getStateRotina(): RotinaPesca {
        return stateRotina
    }

    fun resetarRotina() {
        stateRotina = JogarIsca()
    }
}