package rpg.model.herois;

import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;
import rpg.utils.AsciiArt;

public class Arqueiro extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    private int foco;
    private static final int FOCO_MINIMO = 3;
    private static final double VIDA_MINIMA_HABILIDADE = 25.0;

    public Arqueiro(String nome) {
        super(nome, TipoPersonagem.ARQUEIRO, 145, 33, 13, 115);
        this.foco = 0;
    }

    @Override
    public int atacar() {
        foco = Math.min(foco + 1, 4);
        return getAtaque() + ThreadLocalRandom.current().nextInt(1, 10);
    }

    @Override
    public int usarHabilidade(PersonagemBase alvo) {
        if (!podeUsarHabilidade(alvo)) {
            return -1;
        }

        gastarEnergia(getCustoHabilidade());
        foco = 0;
        return (int) Math.round(getAtaque() * 2.1) + 15;
    }

    @Override
    public int getCustoHabilidade() {
        return 48;
    }

    @Override
    public String getNomeHabilidade() {
        return "Disparo Perfurante";
    }

    @Override
    public String descreverRequisitosHabilidade() {
        return "Energia >= " + getCustoHabilidade()
                + ", Foco >= " + FOCO_MINIMO
                + ", Vida >= " + (int) VIDA_MINIMA_HABILIDADE + "%";
    }

    @Override
    public String descreverProgressoHabilidade(PersonagemBase alvo) {
        int faltaEnergia = Math.max(0, getCustoHabilidade() - getEnergiaAtual());
        int faltaFoco = Math.max(0, FOCO_MINIMO - foco);
        double faltaVida = Math.max(0, VIDA_MINIMA_HABILIDADE - getPercentualVida());

        String energiaTexto = faltaEnergia == 0
                ? "Energia OK"
                : "Faltam " + faltaEnergia + " de energia";
        String focoTexto = faltaFoco == 0
                ? "Foco maximo pronto"
                : "Falta " + faltaFoco + " carga de foco";
        String vidaTexto = faltaVida <= 0
                ? "Vida segura para habilidade"
                : "Vida baixa: faltam " + (int) Math.ceil(faltaVida) + "%";

        return energiaTexto + " | " + focoTexto + " | " + vidaTexto;
    }

    @Override
    public boolean podeUsarHabilidade(PersonagemBase alvo) {
        boolean energiaOk = getEnergiaAtual() >= getCustoHabilidade();
        boolean focoOk = foco >= FOCO_MINIMO;
        boolean vidaOk = getPercentualVida() >= VIDA_MINIMA_HABILIDADE;
        return energiaOk && focoOk && vidaOk;
    }

    @Override
    public String recuperar() {
        curarVida(14);
        recuperarEnergia(24);
        foco = Math.min(foco + 2, 4);
        return getNome() + " recuou, ajustou a postura e recuperou o ritmo.";
    }

    @Override
    public String getAsciiArte() {
        return AsciiArt.ARQUEIRO;
    }
}
