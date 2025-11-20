package abstracts

import pesca.tela.StateEnum

interface Rotina {
    fun executaRotina()
    fun getProximaRotina() : StateEnum
}