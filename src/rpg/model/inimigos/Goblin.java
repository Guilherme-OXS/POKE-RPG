package rpg.model.inimigos;

import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;
import rpg.utils.AsciiArt;

public class Goblin extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    private static final double VIDA_MINIMA_ALVO = 35.0;

    public Goblin(String nome) {
        super(nome, TipoPersonagem.GOBLIN, 95, 26, 8, 85);
    }

    @Override
    public int atacar() {
        return getAtaque() + ThreadLocalRandom.current().nextInt(3, 12);
    }

    @Override
    public int usarHabilidade(PersonagemBase alvo) {
        if (!podeUsarHabilidade(alvo)) {
            return -1;
        }

        gastarEnergia(getCustoHabilidade());
        return (int) Math.round(getAtaque() * 1.7) + 12;
    }

    @Override
    public int getCustoHabilidade() {
        return 30;
    }

    @Override
    public String getNomeHabilidade() {
        return "Golpe Pelas Sombras";
    }

    @Override
    public String descreverRequisitosHabilidade() {
        return "Energia >= " + getCustoHabilidade() + ", Alvo com vida > " + (int) VIDA_MINIMA_ALVO + "%";
    }

    @Override
    public String descreverProgressoHabilidade(PersonagemBase alvo) {
        int faltaEnergia = Math.max(0, getCustoHabilidade() - getEnergiaAtual());
        double vidaAlvo = alvo == null ? 0 : alvo.getPercentualVida();
        double faltaVidaAlvo = Math.max(0, VIDA_MINIMA_ALVO - vidaAlvo + 0.1);

        String energiaTexto = faltaEnergia == 0 ? "Energia OK" : "Faltam " + faltaEnergia + " de energia";
        String alvoTexto = vidaAlvo > VIDA_MINIMA_ALVO
                ? "Alvo vulneravel para emboscada"
                : "Alvo ja esta fraco (faltam " + (int) Math.ceil(faltaVidaAlvo) + "% para o minimo)";
        return energiaTexto + " | " + alvoTexto;
    }

    @Override
    public boolean podeUsarHabilidade(PersonagemBase alvo) {
        boolean energiaOk = getEnergiaAtual() >= getCustoHabilidade();
        boolean alvoForte = alvo != null && alvo.getPercentualVida() > VIDA_MINIMA_ALVO;
        return energiaOk && alvoForte;
    }

    @Override
    public String recuperar() {
        curarVida(8);
        recuperarEnergia(22);
        return getNome() + " reapareceu das sombras com folego renovado.";
    }

    @Override
    public String getAsciiArte() {
        return AsciiArt.GOBLIN;
    }
}
