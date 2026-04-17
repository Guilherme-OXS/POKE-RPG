package rpg.model.inimigos;

import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;
import rpg.utils.AsciiArt;

public class Orc extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    private static final double VIDA_MAXIMA_PARA_HABILIDADE = 65.0;

    public Orc(String nome) {
        super(nome, TipoPersonagem.ORC, 125, 29, 11, 70);
    }

    @Override
    public int atacar() {
        return getAtaque() + ThreadLocalRandom.current().nextInt(0, 7);
    }

    @Override
    public int usarHabilidade(PersonagemBase alvo) {
        if (!podeUsarHabilidade(alvo)) {
            return -1;
        }
        gastarEnergia(getCustoHabilidade());
        return (int) Math.round(getAtaque() * 1.8) + 8;
    }

    @Override
    public int getCustoHabilidade() {
        return 35;
    }

    @Override
    public String getNomeHabilidade() {
        return "Ruptura Brutal";
    }

    @Override
    public String descreverRequisitosHabilidade() {
        return "Energia >= " + getCustoHabilidade() + ", Vida <= " + (int) VIDA_MAXIMA_PARA_HABILIDADE + "%";
    }

    @Override
    public String descreverProgressoHabilidade(PersonagemBase alvo) {
        int faltaEnergia = Math.max(0, getCustoHabilidade() - getEnergiaAtual());
        double excessoVida = Math.max(0, getPercentualVida() - VIDA_MAXIMA_PARA_HABILIDADE);

        String energiaTexto = faltaEnergia == 0 ? "Energia OK" : "Faltam " + faltaEnergia + " de energia";
        String vidaTexto = excessoVida <= 0
                ? "Furia de combate ativa"
                : "Vida ainda alta (" + (int) Math.ceil(excessoVida) + "% acima do limite)";
        return energiaTexto + " | " + vidaTexto;
    }

    @Override
    public boolean podeUsarHabilidade(PersonagemBase alvo) {
        return getEnergiaAtual() >= getCustoHabilidade() && getPercentualVida() <= VIDA_MAXIMA_PARA_HABILIDADE;
    }

    @Override
    public String recuperar() {
        curarVida(10);
        recuperarEnergia(18);
        return getNome() + " rugiu e ignorou a dor por alguns instantes.";
    }

    @Override
    public String getAsciiArte() {
        return AsciiArt.ORC;
    }
}
