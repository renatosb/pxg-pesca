package pescaConfig.config.image

import image.ConfigImages
import abstracts.TelaPanel
import java.awt.GridLayout
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel

object PanelConfigImageInfo : TelaPanel {

    private val panel: JPanel = JPanel()
    private val exampleImageLabel: JLabel = JLabel()
    private val descImageLabel: JLabel = JLabel(
        "<html> 1 - Utilize 2 moedas posicionadas horizontalmente. Com a segunda um SQM abaixo do personagem. <br>" +
                "2 - Ao Clicar no botão Moeda Esquerda, posicione o mouse em cima da moeda e aguarde 3 segundos. Repetir para a moeda direita. <br>" +
                "3 - Após obter as duas posições(conferir na pescaConfig.tela do bot), clicar em Calcular Raio. <br>" +
                "4 - Obtendo Raio, clique em calcular SQM do Centro da Tela, se tudo tiver sido feito da forma correta, o arquivo de configIni.txt será gerad com sucesso. </html>"
    )

    private val listaLabels: List<JLabel> = listOf(
        exampleImageLabel,
        descImageLabel,
    )

    init {
        panel.layout = GridLayout(listaLabels.size, 1)
        exampleImageLabel.icon = ImageIcon(ConfigImages.getSqmFinderHorizontalImage())
        listaLabels.map { panel.add(it) }
    }

    override fun getPanel(): JPanel {
        return panel
    }
}