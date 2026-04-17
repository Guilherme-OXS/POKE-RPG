package rpg.model.herois;

import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;
import rpg.utils.AsciiArt;

public class Mago extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    private int runasCarregadas;
    private static final int RUNAS_MINIMAS = 2;
    private static final double ENERGIA_PERCENTUAL_MINIMA = 60.0;

    public Mago(String nome) {
        super(nome, TipoPersonagem.MAGO, 130, 36, 10, 140);
        this.runasCarregadas = 0;
    }

    @Override
    public int atacar() {
        runasCarregadas = Math.min(runasCarregadas + 1, 3);
        return getAtaque() + ThreadLocalRandom.current().nextInt(2, 11);
    }

    @Override
    public int usarHabilidade(PersonagemBase alvo) {
        if (!podeUsarHabilidade(alvo)) {
            return -1;
        }

        gastarEnergia(getCustoHabilidade());
        runasCarregadas = 0;
        return (int) Math.round(getAtaque() * 2.0) + 22;
    }

    @Override
    public int getCustoHabilidade() {
        return 65;
    }

    @Override
    public String getNomeHabilidade() {
        return "Ritual da Ruptura";
    }

    @Override
    public String descreverRequisitosHabilidade() {
        return "Energia >= " + getCustoHabilidade()
                + ", Energia >= " + (int) ENERGIA_PERCENTUAL_MINIMA + "%"
                + ", Runas >= " + RUNAS_MINIMAS;
    }

    @Override
    public String descreverProgressoHabilidade(PersonagemBase alvo) {
        int faltaEnergia = Math.max(0, getCustoHabilidade() - getEnergiaAtual());
        int faltaRunas = Math.max(0, RUNAS_MINIMAS - runasCarregadas);
        double faltaPercentual = Math.max(0, ENERGIA_PERCENTUAL_MINIMA - getPercentualEnergia());

        String energiaTexto = faltaEnergia == 0
                ? "Energia bruta OK"
                : "Faltam " + faltaEnergia + " de energia";
        String percentualTexto = faltaPercentual <= 0
                ? "Reserva arcana OK"
                : "Faltam " + (int) Math.ceil(faltaPercentual) + "% de energia percentual";
        String runaTexto = faltaRunas == 0
                ? "Runas prontas"
                : "Faltam " + faltaRunas + " runas carregadas";

        return energiaTexto + " | " + percentualTexto + " | " + runaTexto;
    }

    @Override
    public boolean podeUsarHabilidade(PersonagemBase alvo) {
        boolean energiaAlta = getPercentualEnergia() >= ENERGIA_PERCENTUAL_MINIMA;
        boolean energiaSuficiente = getEnergiaAtual() >= getCustoHabilidade();
        boolean runaPronta = runasCarregadas >= RUNAS_MINIMAS;
        return energiaAlta && energiaSuficiente && runaPronta;
    }

    @Override
    public String recuperar() {
        curarVida(12);
        recuperarEnergia(34);
        runasCarregadas = Math.min(runasCarregadas + 1, 3);
        return getNome() + " canalizou mana e estabilizou a energia arcana.";
    }

    @Override
    public String getAsciiArte() {
        return AsciiArt.MAGO;
    }
}
