package rpg.engine;

import java.util.Locale;

import rpg.model.TipoPersonagem;
import rpg.utils.AsciiArt;

public final class Narrativa {
    public enum RotaNarrativa {
        PADRAO,
        GUILHERME,
        GABRIELL_LOWRAN,
        BRUNO,
        ENZO,
        FELIPE
    }

    public static final class ChefeConfig {
        private final String nome;
        private final TipoPersonagem tipo;
        private final String habilidade;
        private final String asciiArte;
        private final int vidaMaxima;
        private final int ataque;
        private final int defesa;
        private final int energiaMaxima;
        private final int custoHabilidade;
        private final double multiplicadorHabilidade;
        private final int bonusHabilidade;
        private final int cura;
        private final int energiaRecuperada;
        private final String textoRecuperacao;

        private ChefeConfig(
                String nome,
            TipoPersonagem tipo,
                String habilidade,
                String asciiArte,
                int vidaMaxima,
                int ataque,
                int defesa,
                int energiaMaxima,
                int custoHabilidade,
                double multiplicadorHabilidade,
                int bonusHabilidade,
                int cura,
                int energiaRecuperada,
                String textoRecuperacao) {
            this.nome = nome;
            this.tipo = tipo == null ? TipoPersonagem.CHEFE_REI_DEMONIO : tipo;
            this.habilidade = habilidade;
            this.asciiArte = asciiArte;
            this.vidaMaxima = vidaMaxima;
            this.ataque = ataque;
            this.defesa = defesa;
            this.energiaMaxima = energiaMaxima;
            this.custoHabilidade = custoHabilidade;
            this.multiplicadorHabilidade = multiplicadorHabilidade;
            this.bonusHabilidade = bonusHabilidade;
            this.cura = cura;
            this.energiaRecuperada = energiaRecuperada;
            this.textoRecuperacao = textoRecuperacao;
        }

        public String getNome() {
            return nome;
        }

        public TipoPersonagem getTipo() {
            return tipo;
        }

        public String getHabilidade() {
            return habilidade;
        }

        public String getAsciiArte() {
            return asciiArte;
        }

        public int getVidaMaxima() {
            return vidaMaxima;
        }

        public int getAtaque() {
            return ataque;
        }

        public int getDefesa() {
            return defesa;
        }

        public int getEnergiaMaxima() {
            return energiaMaxima;
        }

        public int getCustoHabilidade() {
            return custoHabilidade;
        }

        public double getMultiplicadorHabilidade() {
            return multiplicadorHabilidade;
        }

        public int getBonusHabilidade() {
            return bonusHabilidade;
        }

        public int getCura() {
            return cura;
        }

        public int getEnergiaRecuperada() {
            return energiaRecuperada;
        }

        public String getTextoRecuperacao() {
            return textoRecuperacao;
        }
    }

    private Narrativa() {
    }

    public static RotaNarrativa detectarRota(String nomeHeroi) {
        String normalizado = normalizar(nomeHeroi);

        if ("guilherme".equals(normalizado)) {
            return RotaNarrativa.GUILHERME;
        }
        if ("gabriell".equals(normalizado) || "lowran".equals(normalizado)) {
            return RotaNarrativa.GABRIELL_LOWRAN;
        }
        if ("bruno".equals(normalizado)) {
            return RotaNarrativa.BRUNO;
        }
        if ("enzo".equals(normalizado)) {
            return RotaNarrativa.ENZO;
        }
        if ("felipe".equals(normalizado)) {
            return RotaNarrativa.FELIPE;
        }
        return RotaNarrativa.PADRAO;
    }

    public static String[] abertura(RotaNarrativa rota, String nomeHeroi) {
        switch (rota) {
            case GUILHERME:
                return new String[] {
                        nomeHeroi + ", voce entrou na dungeon por fama, ouro e gloria.",
                        "No escuro, monitores quebrados piscam sem energia.",
                        "Uma voz robotica sussurra: alvo humano identificado."
                };
            case GABRIELL_LOWRAN:
                return new String[] {
                        nomeHeroi + ", voce entrou na dungeon por fama, ouro e gloria.",
                        "Paredes de pedra exibem simbolos parecidos com notas e rubricas.",
                        "No fundo do corredor, alguem escreveu: prova final sem consulta."
                };
            case BRUNO:
                return new String[] {
                        nomeHeroi + ", voce entrou na dungeon por fama, ouro e gloria.",
                        "O ar e frio, mas existe um cheiro estranho de feira antiga.",
                        "As sombras parecem redondas demais para serem humanas."
                };
            case ENZO:
                return new String[] {
                        nomeHeroi + ", voce entrou na dungeon por fama, ouro e gloria.",
                        "A cada passo, sua sombra fica mais curta e mais densa.",
                        "Uma frase aparece no teto: quando chegar ao fim, encare a verdade."
                };
            case FELIPE:
                return new String[] {
                        nomeHeroi + ", voce entrou na dungeon por fama, ouro e gloria.",
                        "As correntes de ar fazem um som de sopro metalico.",
                        "A caverna vibra como se escondesse uma banda no abismo."
                };
            default:
                return new String[] {
                        nomeHeroi + ", voce entrou na dungeon por fama, ouro e gloria.",
                        "A noite engoliu o reino e so restou pedra, sangue e promessas.",
                        "No fim do labirinto, o Rei Demonio espera por um desafiante."
                };
        }
    }

    public static String[] meioDaJornada(RotaNarrativa rota) {
        switch (rota) {
            case GUILHERME:
                return new String[] {
                        "Os goblins estao usando codigos gravados em placas de circuito.",
                        "A energia da dungeon muda de magica para digital.",
                        "Agora ficou claro: a IA esta controlando tudo."
                };
            case GABRIELL_LOWRAN:
                return new String[] {
                        "Um corredor inteiro esta coberto por questoes discursivas.",
                        "Voce percebe que o terror desta dungeon e academico.",
                        "Algo chamado Prova do Andre esta no fim do caminho."
                };
            case BRUNO:
                return new String[] {
                        "O chao apresenta trilhas de sementes negras e cascas verdes.",
                        "Cada monstro derrotado deixa um suco vermelho no piso.",
                        "A Melancia Ancestral acordou em alguma camara profunda."
                };
            case ENZO:
                return new String[] {
                        "As tochas apagaram sem vento e sem explicacao.",
                        "Uma pupila enorme foi vista no teto da sala anterior.",
                        "O chefe final nao e um monstro: e a Verdade Sem Rosto."
                };
            case FELIPE:
                return new String[] {
                        "Os ecos da dungeon viraram notas desafinadas de guerra.",
                        "Armaduras quebradas vibram como se fossem instrumentos.",
                        "No salao final, o Trompete do Abismo esta esperando."
                };
            default:
                return new String[] {
                        "O caminho para o fundo da dungeon ficou mais sombrio.",
                        "Cadaveres de soldados apontam para o trono subterraneo.",
                        "Voce sente a presenca do Rei Demonio cada vez mais perto."
                };
        }
    }

    public static String[] antesDoChefe(RotaNarrativa rota) {
        switch (rota) {
            case GUILHERME:
                return new String[] {
                        "Um portao de aco se abre sem ninguem tocar.",
                        "No centro da sala, um nucleo azul acende.",
                        "Chefe final detectado: IA Primordial."
                };
            case GABRIELL_LOWRAN:
                return new String[] {
                        "Voce entra em uma sala com carteiras quebradas.",
                        "No quadro, uma mensagem: sem pontos extras hoje.",
                        "Chefe final detectado: Prova do Andre."
                };
            case BRUNO:
                return new String[] {
                        "A camara final tem sementes no teto e no chao.",
                        "Um casco gigante rola na sua direcao.",
                        "Chefe final detectado: Melancia Ancestral."
                };
            case ENZO:
                return new String[] {
                        "Tudo fica silencioso, ate o seu proprio coracao.",
                        "Uma fissura abre no ar e um olho surge no vazio.",
                        "Chefe final detectado: Verdade Sem Rosto."
                };
            case FELIPE:
                return new String[] {
                        "O salao vibra com um acorde que corta os ossos.",
                        "Metal retorcido forma um instrumento vivo.",
                        "Chefe final detectado: Trompete do Abismo."
                };
            default:
                return new String[] {
                        "Tronos de ossos cercam o ultimo salao.",
                        "Uma coroa de fogo se ergue na escuridao.",
                        "Chefe final detectado: Rei Demonio de Cindral."
                };
        }
    }

    public static String[] dialogoDaFase(RotaNarrativa rota, int fase, String nomeInimigo) {
        String inimigo = nomeInimigo == null ? "o inimigo" : nomeInimigo;

        switch (rota) {
            case GUILHERME:
                return new String[] {
                        "Interferencia detectada no setor " + fase + ".",
                        inimigo + " recebeu comandos diretos da IA."
                };
            case GABRIELL_LOWRAN:
                return new String[] {
                        "A parede registra criterios de avaliacao para a fase " + fase + ".",
                        inimigo + " avanca como se fosse o fiscal da prova."
                };
            case BRUNO:
                return new String[] {
                        "Sementes quebradas marcam o corredor da fase " + fase + ".",
                        inimigo + " aparece coberto por um brilho avermelhado."
                };
            case ENZO:
                return new String[] {
                        "As tochas tremem quando a fase " + fase + " se abre.",
                        inimigo + " evita encarar voce diretamente."
                };
            case FELIPE:
                return new String[] {
                        "Ecos metalicos anunciam a fase " + fase + ".",
                        inimigo + " surge no ritmo de um acorde sombrio."
                };
            default:
                return new String[] {
                        "As correntes da dungeon ecoam na fase " + fase + ".",
                        inimigo + " bloqueia seu caminho no escuro."
                };
        }
    }

    public static String[] epilogoVitoria(RotaNarrativa rota, String nomeHeroi) {
        switch (rota) {
            case GUILHERME:
                return new String[] {
                        nomeHeroi + " esmagou o nucleo da IA Primordial.",
                        "As paredes digitais da dungeon colapsaram em silencio.",
                        "Final secreto: O sistema foi derrotado por um humano."
                };
            case GABRIELL_LOWRAN:
                return new String[] {
                        nomeHeroi + " rasgou a Prova do Andre no golpe final.",
                        "As notas zero viraram cinza no ar da caverna.",
                        "Final secreto: Sem prova, sem medo, sem recuperacao."
                };
            case BRUNO:
                return new String[] {
                        nomeHeroi + " venceu a Melancia Ancestral em combate direto.",
                        "Sementes explodiram como chuva negra no salao final.",
                        "Final secreto: O fruto caiu e a lenda cresceu."
                };
            case ENZO:
                return new String[] {
                        nomeHeroi + " enfrentou a Verdade Sem Rosto e nao recuou.",
                        "A pupila no vazio se fechou pela primeira vez.",
                        "Final secreto: Quem encarou a verdade saiu diferente."
                };
            case FELIPE:
                return new String[] {
                        nomeHeroi + " silenciou o Trompete do Abismo.",
                        "A ultima nota ecoou e o salao voltou ao vazio.",
                        "Final secreto: O som da guerra foi interrompido."
                };
            default:
                return new String[] {
                        nomeHeroi + " derrubou o Rei Demonio de Cindral.",
                        "A coroa de fogo apagou e o trono virou poeira.",
                        "Final padrao: O reino tem um novo nome para temer e admirar."
                };
        }
    }

    public static String[] epilogoDerrota(RotaNarrativa rota, String nomeHeroi) {
        switch (rota) {
            case GUILHERME:
                return new String[] {
                        "A IA Primordial registrou a queda de " + nomeHeroi + ".",
                        "A dungeon continuou rodando em modo de exterminio."
                };
            case GABRIELL_LOWRAN:
                return new String[] {
                        "A Prova do Andre marcou " + nomeHeroi + " com nota final zero.",
                        "A sala voltou ao silencio de uma noite sem revisao."
                };
            case BRUNO:
                return new String[] {
                        "A Melancia Ancestral continuou rolando sobre os corredores.",
                        nomeHeroi + " virou mais um mito esmagado na dungeon."
                };
            case ENZO:
                return new String[] {
                        "A Verdade Sem Rosto abriu mais um olho no vazio.",
                        nomeHeroi + " nao suportou o que viu no salao final."
                };
            case FELIPE:
                return new String[] {
                        "O Trompete do Abismo tocou o acorde da derrota.",
                        nomeHeroi + " caiu enquanto o eco tomava a caverna."
                };
            default:
                return new String[] {
                        "O Rei Demonio de Cindral venceu mais um desafiante.",
                        nomeHeroi + " desapareceu entre cinzas e correntes."
                };
        }
    }

    public static ChefeConfig criarChefeFinal(RotaNarrativa rota) {
        switch (rota) {
            case GUILHERME:
                return new ChefeConfig(
                        "Nucleo da IA Primordial",
                        TipoPersonagem.CHEFE_IA,
                        "Sobrecarga de Processamento",
                        AsciiArt.CHEFE_IA,
                        235,
                        42,
                        15,
                        150,
                        60,
                        2.0,
                        20,
                        16,
                        28,
                        "{chefe} reparou modulos e recuperou estabilidade.");
            case GABRIELL_LOWRAN:
                return new ChefeConfig(
                        "A Prova Final do Andre",
                        TipoPersonagem.CHEFE_PROVA,
                        "Correcao Cruel",
                        AsciiArt.CHEFE_PROVA,
                        225,
                        40,
                        16,
                        130,
                        56,
                        2.1,
                        16,
                        18,
                        24,
                        "{chefe} puxou uma nova versao e ficou mais perigosa.");
            case BRUNO:
                return new ChefeConfig(
                        "Melancia Ancestral",
                        TipoPersonagem.CHEFE_MELANCIA,
                        "Impacto da Casca Rubra",
                        AsciiArt.CHEFE_MELANCIA,
                        255,
                        38,
                        20,
                        120,
                        52,
                        2.0,
                        18,
                        22,
                        22,
                        "{chefe} absorveu sementes sombrias e se refez.");
            case ENZO:
                return new ChefeConfig(
                        "A Verdade Sem Rosto",
                        TipoPersonagem.CHEFE_VERDADE,
                        "Olhar Absoluto",
                        AsciiArt.CHEFE_VERDADE,
                        230,
                        44,
                        14,
                        145,
                        61,
                        2.2,
                        18,
                        16,
                        30,
                        "{chefe} distorceu a sala e recuperou sua essencia.");
            case FELIPE:
                return new ChefeConfig(
                        "Trompete do Abismo",
                        TipoPersonagem.CHEFE_TROMPETE,
                        "Fanfarra de Guerra",
                        AsciiArt.CHEFE_TROMPETE,
                        220,
                        41,
                        15,
                        160,
                        58,
                        2.0,
                        19,
                        14,
                        32,
                        "{chefe} soprou o caos e restaurou seu fole sombrio.");
            default:
                return new ChefeConfig(
                        "Rei Demonio de Cindral",
                        TipoPersonagem.CHEFE_REI_DEMONIO,
                        "Lamina do Trono",
                        AsciiArt.CHEFE_REI_DEMONIO,
                        245,
                        43,
                        18,
                        135,
                        57,
                        2.1,
                        17,
                        20,
                        24,
                        "{chefe} reuniu chamas do trono e se restaurou.");
        }
    }

    private static String normalizar(String nome) {
        if (nome == null) {
            return "";
        }
        return nome.trim().toLowerCase(Locale.ROOT);
    }
}