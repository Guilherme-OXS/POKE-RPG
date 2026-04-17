package rpg.model.inimigos;

import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;
import rpg.utils.AsciiArt;

public class DragaoMenor extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    private static final double VIDA_MAXIMA_PARA_HABILIDADE = 70.0;
    private static final double VIDA_MINIMA_ALVO = 20.0;

    public DragaoMenor(String nome) {
        super(nome, TipoPersonagem.DRAGAO, 220, 38, 16, 120);
    }

    @Override
    public int atacar() {
        return getAtaque() + ThreadLocalRandom.current().nextInt(4, 14);
    }

    @Override
    public int usarHabilidade(PersonagemBase alvo) {
        if (!podeUsarHabilidade(alvo)) {
            return -1;
        }

        gastarEnergia(getCustoHabilidade());
        return (int) Math.round(getAtaque() * 2.0) + 16;
    }

    @Override
    public int getCustoHabilidade() {
        return 55;
    }

    @Override
    public String getNomeHabilidade() {
        return "Sopro Cataclismico";
    }

    @Override
    public String descreverRequisitosHabilidade() {
        return "Energia >= " + getCustoHabilidade()
                + ", Vida <= " + (int) VIDA_MAXIMA_PARA_HABILIDADE + "%"
                + ", Alvo com vida >= " + (int) VIDA_MINIMA_ALVO + "%";
    }

    @Override
    public String descreverProgressoHabilidade(PersonagemBase alvo) {
        int faltaEnergia = Math.max(0, getCustoHabilidade() - getEnergiaAtual());
        double excessoVida = Math.max(0, getPercentualVida() - VIDA_MAXIMA_PARA_HABILIDADE);
        double vidaAlvo = alvo == null ? 0 : alvo.getPercentualVida();
        double faltaVidaAlvo = Math.max(0, VIDA_MINIMA_ALVO - vidaAlvo);

        String energiaTexto = faltaEnergia == 0 ? "Energia OK" : "Faltam " + faltaEnergia + " de energia";
        String vidaTexto = excessoVida <= 0
                ? "Fase de furia ativada"
                : "Ainda falta entrar em furia (" + (int) Math.ceil(excessoVida) + "% acima)";
        String alvoTexto = faltaVidaAlvo <= 0
                ? "Alvo valido para execucao"
                : "Alvo fraco demais (faltam " + (int) Math.ceil(faltaVidaAlvo) + "% de vida no alvo)";

        return energiaTexto + " | " + vidaTexto + " | " + alvoTexto;
    }

    @Override
    public boolean podeUsarHabilidade(PersonagemBase alvo) {
        boolean energiaOk = getEnergiaAtual() >= getCustoHabilidade();
        boolean vidaBaixa = getPercentualVida() <= VIDA_MAXIMA_PARA_HABILIDADE;
        boolean alvoVivo = alvo != null && alvo.getPercentualVida() >= VIDA_MINIMA_ALVO;
        return energiaOk && vidaBaixa && alvoVivo;
    }

    @Override
    public String recuperar() {
        curarVida(18);
        recuperarEnergia(26);
        return getNome() + " fechou as asas e acelerou a recuperacao.";
    }

    @Override
    public String getAsciiArte() {
        return AsciiArt.DRAGAO;
    }
}
