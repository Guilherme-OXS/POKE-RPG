package rpg.model.herois;

import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;
import rpg.utils.AsciiArt;

public class Guerreiro extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    private int cargaFuria;
    private static final int FURIA_MINIMA = 2;
    private static final double VIDA_LIMITE_HABILIDADE = 45.0;

    public Guerreiro(String nome) {
        super(nome, TipoPersonagem.GUERREIRO, 170, 30, 18, 100);
        this.cargaFuria = 0;
    }

    @Override
    public int atacar() {
        cargaFuria = Math.min(cargaFuria + 1, 3);
        return getAtaque() + ThreadLocalRandom.current().nextInt(0, 8);
    }

    @Override
    public int usarHabilidade(PersonagemBase alvo) {
        if (!podeUsarHabilidade(alvo)) {
            return -1;
        }

        gastarEnergia(getCustoHabilidade());
        cargaFuria = 0;
        return (int) Math.round(getAtaque() * 2.3) + 8;
    }

    @Override
    public int getCustoHabilidade() {
        return 50;
    }

    @Override
    public String getNomeHabilidade() {
        return "Impacto de Aco";
    }

    @Override
    public String descreverRequisitosHabilidade() {
        return "Energia >= " + getCustoHabilidade()
                + ", Furia >= " + FURIA_MINIMA
                + ", Vida <= " + (int) VIDA_LIMITE_HABILIDADE + "%";
    }

    @Override
    public String descreverProgressoHabilidade(PersonagemBase alvo) {
        int faltaEnergia = Math.max(0, getCustoHabilidade() - getEnergiaAtual());
        int faltaFuria = Math.max(0, FURIA_MINIMA - cargaFuria);
        double excessoVida = Math.max(0, getPercentualVida() - VIDA_LIMITE_HABILIDADE);

        String energiaTexto = faltaEnergia == 0
                ? "Energia OK"
                : "Faltam " + faltaEnergia + " de energia";
        String furiaTexto = faltaFuria == 0
                ? "Furia OK"
                : "Faltam " + faltaFuria + " cargas de furia";
        String vidaTexto = excessoVida <= 0
                ? "Vida em zona de furia"
                : "Vida acima do limite em " + (int) Math.ceil(excessoVida) + "%";

        return energiaTexto + " | " + furiaTexto + " | " + vidaTexto;
    }

    @Override
    public boolean podeUsarHabilidade(PersonagemBase alvo) {
        boolean vidaCritica = getPercentualVida() <= VIDA_LIMITE_HABILIDADE;
        boolean energiaOk = getEnergiaAtual() >= getCustoHabilidade();
        boolean furiaPronta = cargaFuria >= FURIA_MINIMA;
        return vidaCritica && energiaOk && furiaPronta;
    }

    @Override
    public String recuperar() {
        curarVida(16);
        recuperarEnergia(28);
        return getNome() + " elevou o escudo, respirou fundo e recuperou recursos.";
    }

    @Override
    public String getAsciiArte() {
        return AsciiArt.GUERREIRO;
    }
}
