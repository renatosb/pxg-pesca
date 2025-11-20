package pesca.tela

enum class StateEnum(val description: String) {
    JOGAR_ISCA("Lançando isca"),
    AGUARDAR_PEIXE("Aguardando morder a isca"),
    PUXAR_ISCA("Puxando isca"),
    RESOLVER_PUZZLE("Resolvendo minigame"),
    USAR_REVIVE("Revivendo Pokemon"),
    ATACAR("Atacando"),
    COLETAR_LOOT("Coletando loot"),
    JOGAR_BALL("Tacando a ball"),
}