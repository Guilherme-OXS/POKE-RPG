package rpg.model.inimigos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;
import rpg.utils.AsciiArt;

public class Goku extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    private static final int CHANCE_BASE_EVOLUCAO = 3;
    private static final int CHANCE_BONUS_FASE_CRITICA = 2;
    private static final int CHANCE_MAXIMA_EVOLUCAO = 9;

    private static final double LIMIAR_VIDA_CRITICA = 50.0;

    private final boolean[] marcosNarrativos = new boolean[8];
    private int indiceFormaAtual;

    private enum FormaGoku {
        BASE("Base", 0, 0, 0, 0, 70, 2.8, 42, 34, 48, 100.0, "Kamehameha Absoluto",
            "Goku entra em postura serena e diz que quer testar sua forca sem truques."),
        KAIOKEN("Kaioken", 8, 3, 18, 18, 72, 2.95, 48, 36, 50, 90.0, "Kamehameha Kaioken",
            "Um clarao avermelhado explode ao redor do corpo de Goku. O proprio ar parece rasgar.") ,
        SUPER_SAIYAJIN("Super Saiyajin", 10, 3, 24, 20, 74, 3.1, 54, 38, 52, 82.0, "Kamehameha Dourado",
            "O cabelo dourado sobe em chamas e a voz de Goku fica mais firme: a lenda acordou."),
        SUPER_SAIYAJIN_2("Super Saiyajin 2", 10, 4, 30, 22, 76, 3.25, 62, 40, 54, 74.0, "Choque do Relampago Divino",
            "Raios cortam o campo de batalha. Goku sorri como se tivesse encontrado um novo limite."),
        SUPER_SAIYAJIN_3("Super Saiyajin 3", 11, 4, 34, 24, 78, 3.4, 70, 42, 56, 66.0, "Rugido do Dragao Celeste",
            "As sobrancelhas somem, a aura cresce e a arena sente o peso de um rei das batalhas."),
        SUPER_SAIYAJIN_4("Super Saiyajin 4", 12, 4, 36, 24, 80, 3.6, 80, 44, 58, 58.0, "Impacto Primordial",
            "A energia toma uma forma selvagem e antiga. Goku parece meio homem, meio mito primordial."),
        SUPER_SAIYAJIN_DEUS("Super Saiyajin Deus", 12, 5, 38, 26, 82, 3.8, 90, 46, 62, 50.0, "Punho da Chama Divina",
            "O ki muda de cor e fica silencioso por um instante. Depois, o mundo inteiro percebe a pressao divina."),
        SUPER_SAIYAJIN_BLUE("Super Saiyajin Blue", 13, 5, 40, 26, 84, 4.0, 102, 48, 64, 42.0, "Kamehameha Azul Celeste",
            "O azul explode como um ceu em furia controlada. Goku fala baixo, mas cada palavra pesa toneladas."),
        SUPER_SAIYAJIN_BLUE_KAIOKEN("Super Saiyajin Blue Kaioken", 14, 6, 44, 28, 86, 4.3, 116, 50, 66, 34.0, "Kaioken Azul Total",
            "Azul e vermelho se chocam ao mesmo tempo. O corpo de Goku geme, mas ele avanca sem hesitar."),
        INSTINTO_SUPERIOR_SINAL("Instinto Superior Sinal", 15, 6, 46, 30, 88, 4.6, 132, 52, 70, 26.0, "Surto Instintivo",
            "O olhar de Goku fica vazio e preciso. Ele para de pensar e passa a se mover como tempestade."),
        INSTINTO_SUPERIOR_COMPLETO("Instinto Superior Completo", 16, 7, 50, 32, 90, 4.95, 150, 54, 74, 18.0,
            "Kamehameha da Serenidade Absoluta",
            "A pele, o ki e o silencio entram em harmonia. O Instinto Superior Completo toma conta da arena."),
        VERDADEIRO_INSTINTO_SUPERIOR("Verdadeiro Instinto Superior", 18, 8, 55, 36, 92, 5.35, 174, 58, 78, 10.0,
            "Eclipse do Instinto Supremo",
            "Goku sorri com a paz de quem encontrou seu proprio caminho. O verdadeiro instinto desperta por inteiro.");

        private final String nome;
        private final int bonusAtaque;
        private final int bonusDefesa;
        private final int bonusVida;
        private final int bonusEnergia;
        private final int custoHabilidade;
        private final double multiplicadorHabilidade;
        private final int bonusDanoHabilidade;
        private final int curaVida;
        private final int recuperacaoEnergia;
        private final double limiarDespertar;
        private final String nomeHabilidade;
        private final String historiaEvolucao;

        FormaGoku(
                String nome,
                int bonusAtaque,
                int bonusDefesa,
                int bonusVida,
                int bonusEnergia,
                int custoHabilidade,
                double multiplicadorHabilidade,
                int bonusDanoHabilidade,
                int curaVida,
                int recuperacaoEnergia,
                double limiarDespertar,
                String nomeHabilidade,
                String historiaEvolucao) {
            this.nome = nome;
            this.bonusAtaque = bonusAtaque;
            this.bonusDefesa = bonusDefesa;
            this.bonusVida = bonusVida;
            this.bonusEnergia = bonusEnergia;
            this.custoHabilidade = custoHabilidade;
            this.multiplicadorHabilidade = multiplicadorHabilidade;
            this.bonusDanoHabilidade = bonusDanoHabilidade;
            this.curaVida = curaVida;
            this.recuperacaoEnergia = recuperacaoEnergia;
            this.limiarDespertar = limiarDespertar;
            this.nomeHabilidade = nomeHabilidade;
            this.historiaEvolucao = historiaEvolucao;
        }
    }

    public Goku() {
        super("Goku Instinto Supremo", TipoPersonagem.GOKU, 420, 68, 30, 240);
        this.indiceFormaAtual = 0;
    }

    @Override
    public int atacar() {
        int bonusMinimo = 8 + (indiceFormaAtual * 2);
        int bonusMaximoExclusivo = 21 + (indiceFormaAtual * 3);
        return getAtaque() + ThreadLocalRandom.current().nextInt(bonusMinimo, bonusMaximoExclusivo);
    }

    @Override
    public int usarHabilidade(PersonagemBase alvo) {
        if (!podeUsarHabilidade(alvo)) {
            return -1;
        }

        gastarEnergia(getCustoHabilidade());
        FormaGoku forma = formaAtual();
        return (int) Math.round(getAtaque() * forma.multiplicadorHabilidade) + forma.bonusDanoHabilidade;
    }

    @Override
    public int getCustoHabilidade() {
        return formaAtual().custoHabilidade;
    }

    @Override
    public String getNomeHabilidade() {
        return formaAtual().nomeHabilidade;
    }

    @Override
    public String descreverRequisitosHabilidade() {
        return "Energia >= " + getCustoHabilidade() + ", Alvo vivo, Forma ativa: " + getNomeFormaAtual();
    }

    @Override
    public String descreverProgressoHabilidade(PersonagemBase alvo) {
        int faltaEnergia = Math.max(0, getCustoHabilidade() - getEnergiaAtual());
        boolean alvoValido = alvo != null && alvo.estaVivo();
        String proximaForma = indiceFormaAtual < FormaGoku.values().length - 1
                ? FormaGoku.values()[indiceFormaAtual + 1].nome
                : "MAXIMA";

        String energiaTexto = faltaEnergia == 0 ? "Energia no limite maximo" : "Faltam " + faltaEnergia + " de energia";
        String alvoTexto = alvoValido ? "Alvo fixado" : "Sem alvo valido";
        return energiaTexto + " | " + alvoTexto + " | Forma: " + getNomeFormaAtual() + " -> " + proximaForma;
    }

    @Override
    public boolean podeUsarHabilidade(PersonagemBase alvo) {
        return getEnergiaAtual() >= getCustoHabilidade() && alvo != null && alvo.estaVivo();
    }

    @Override
    public String recuperar() {
        FormaGoku forma = formaAtual();
        curarVida(forma.curaVida);
        recuperarEnergia(forma.recuperacaoEnergia);
        return getNome() + " concentrou o ki em " + forma.nome + " e restaurou seu poder de combate.";
    }

    @Override
    public String getAsciiArte() {
        return AsciiArt.GOKU;
    }

    public String getNomeFormaAtual() {
        return formaAtual().nome;
    }

    public boolean estaNaFormaFinal() {
        return indiceFormaAtual >= FormaGoku.values().length - 1;
    }

    public String tentarEvoluir(PersonagemBase heroi) {
        if (estaNaFormaFinal()) {
            return null;
        }

        FormaGoku proximaForma = FormaGoku.values()[indiceFormaAtual + 1];
        double vidaHeroi = heroi == null ? 100.0 : heroi.getPercentualVida();
        double menorPressao = Math.min(getPercentualVida(), vidaHeroi);
        if (menorPressao > proximaForma.limiarDespertar) {
            return null;
        }

        int chance = calcularChanceEvolucao(heroi);
        if (ThreadLocalRandom.current().nextInt(100) >= chance) {
            return null;
        }

        FormaGoku formaAnterior = formaAtual();
        indiceFormaAtual++;
        FormaGoku novaForma = formaAtual();
        aplicarBoostDaForma(novaForma);

        return formaAnterior.historiaEvolucao + " " + getNome() + " evoluiu de " + formaAnterior.nome + " para " + novaForma.nome
            + ". O Ki esmagador tomou conta da arena.";
    }

    public List<String> coletarNarrativaDaBatalha(PersonagemBase heroi) {
        ArrayList<String> eventos = new ArrayList<String>();
        if (heroi == null) {
            return eventos;
        }

        double vidaHeroi = heroi.getPercentualVida();
        double vidaGoku = getPercentualVida();

        tentarDispararMarco(eventos, 0, vidaHeroi <= 85.0,
                "Seu corpo sente a pressao do ki de Goku. Cada respiracao pesa mais.");
        tentarDispararMarco(eventos, 1, vidaGoku <= 88.0,
                "Goku sorri mesmo ferido: 'Agora a luta ficou interessante!'");
        tentarDispararMarco(eventos, 2, vidaHeroi <= 65.0 && vidaGoku <= 78.0,
                "O choque de auras racha o chao. Voce sente que esta lutando contra um mito vivo.");
        tentarDispararMarco(eventos, 3, vidaHeroi <= 45.0,
                "Seu sangue pulsa no ouvido. A batalha exige tudo o que voce tem.");
        tentarDispararMarco(eventos, 4, vidaGoku <= 55.0,
                "Goku limpa o sangue do rosto e aumenta o foco: 'Ainda nao terminei.'");
        tentarDispararMarco(eventos, 5, vidaHeroi <= 30.0 && vidaGoku <= 40.0,
                "Dois guerreiros no limite. O proximo erro pode decidir o destino da arena.");
        tentarDispararMarco(eventos, 6, vidaHeroi <= 18.0 || vidaGoku <= 18.0,
                "A atmosfera treme. O fim da luta se aproxima com violencia absurda.");
        tentarDispararMarco(eventos, 7, vidaHeroi <= 10.0 && vidaGoku <= 10.0,
                "Ultimo folego contra ultimo folego. Apenas vontade pura sustenta o combate.");

        return eventos;
    }

    private FormaGoku formaAtual() {
        return FormaGoku.values()[indiceFormaAtual];
    }

    private int calcularChanceEvolucao(PersonagemBase heroi) {
        int chance = CHANCE_BASE_EVOLUCAO;
        if (getPercentualVida() <= LIMIAR_VIDA_CRITICA) {
            chance += CHANCE_BONUS_FASE_CRITICA;
        }
        if (heroi != null && heroi.getPercentualVida() <= LIMIAR_VIDA_CRITICA) {
            chance += CHANCE_BONUS_FASE_CRITICA;
        }
        if (indiceFormaAtual >= 6) {
            chance += 1;
        }
        return Math.min(CHANCE_MAXIMA_EVOLUCAO, chance);
    }

    private void aplicarBoostDaForma(FormaGoku forma) {
        setAtaque(getAtaque() + forma.bonusAtaque);
        setDefesa(getDefesa() + forma.bonusDefesa);

        setVidaMaxima(getVidaMaxima() + forma.bonusVida);
        curarVida(Math.max(forma.curaVida, (int) Math.round(getVidaMaxima() * 0.15)));

        setEnergiaMaxima(getEnergiaMaxima() + forma.bonusEnergia);
        recuperarEnergia(Math.max(forma.recuperacaoEnergia, (int) Math.round(getEnergiaMaxima() * 0.20)));
    }

    private void tentarDispararMarco(List<String> eventos, int indice, boolean condicao, String mensagem) {
        if (!marcosNarrativos[indice] && condicao) {
            marcosNarrativos[indice] = true;
            eventos.add(mensagem);
        }
    }
}
