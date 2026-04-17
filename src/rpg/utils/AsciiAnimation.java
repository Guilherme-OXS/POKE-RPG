package rpg.utils;

import java.util.ArrayList;

import rpg.model.TipoPersonagem;

public final class AsciiAnimation {
    public enum TipoAcaoAnimada {
        ATAQUE,
        HABILIDADE,
        DEFESA,
        RECUPERAR,
        ITEM
    }

    private AsciiAnimation() {
    }

    private static final int COLUNA_ESQUERDA = 2;
    private static final int COLUNA_DIREITA = 62;
    private static final int LARGURA_CANVAS = 112;

    // ==========================================
    // PACOTE DE ANIMACAO: GUERREIRO ATAQUE
    // ==========================================
    public static final String[] ANIMACAO_GUERREIRO_ATAQUE = {
            "             \n" +
                    "   ___       \n" +
                    "  [___]   /> \n" +
                    "  (o_o)  //  \n" +
                    " (\\| |/)//   \n" +
                    "   | | //    \n" +
                    "  /   \\      \n" +
                    " /_____\\     \n",

            "             \n" +
                    "   ___    /| \n" +
                    "  [___]  / | \n" +
                    "  (-_-) /  | \n" +
                    " (\\| |/)   | \n" +
                    "   | |       \n" +
                    "  /   \\      \n" +
                    " /_____\\     \n",

            "             \n" +
                    "   ___       \n" +
                    "  [___]      \n" +
                    "  (>_<)  ==> \n" +
                    " (\\| |/)     \n" +
                    "   | |       \n" +
                    "  /   \\      \n" +
                    " /_____\\     \n",

            "             \n" +
                    "   ___       \n" +
                    "  [___]   /> \n" +
                    "  (o_o)  //  \n" +
                    " (\\| |/)//   \n" +
                    "   | | //    \n" +
                    "  /   \\      \n" +
                    " /_____\\     \n"
    };

    // ==========================================
    // PACOTE DE ANIMACAO: MAGO CONJURANDO
    // ==========================================
    public static final String[] ANIMACAO_MAGO_CONJURANDO = {
            "             \n" +
                    "   / \\       \n" +
                    "  /___\\   *  \n" +
                    "  (o_o)   |   \n" +
                    " (\\| |/)_/    \n" +
                    "   | |         \n" +
                    "  /   \\       \n" +
                    " /_____\\      \n",

            "             \n" +
                    "   / \\   ( ) \n" +
                    "  /___\\  -*- \n" +
                    "  (-_-)  / \\ \n" +
                    " (\\| |/)_/   \n" +
                    "   | |       \n" +
                    "  /   \\      \n" +
                    " /_____\\     \n",

            "             \n" +
                    "   / \\       \n" +
                    "  /___\\      \n" +
                    "  (>_<)    ~*~*~ \n" +
                    " (\\| |/)--/  \n" +
                    "   | |       \n" +
                    "  /   \\      \n" +
                    " /_____\\     \n"
    };

    public static final String[] ANIMACAO_ARQUEIRO_ATAQUE = {
            "             \n" +
                    "    ^        \n" +
                    "   / \\    |  \n" +
                    "  (o_o)  /|  \n" +
                    " (\\| |/)/ |  \n" +
                    "   | |  \\ |  \n" +
                    "  /   \\   |  \n" +
                    " /_____\\     \n",

            "             \n" +
                    "    ^        \n" +
                    "   / \\   \\| \n" +
                    "  (-_-)   |-->\n" +
                    " (\\| |/) /|  \n" +
                    "   | |  / |   \n" +
                    "  /   \\   |   \n" +
                    " /_____\\      \n",

            "             \n" +
                    "    ^        \n" +
                    "   / \\       \n" +
                    "  (>_<)  ---> \n" +
                    " (\\| |/)      \n" +
                    "   | |        \n" +
                    "  /   \\       \n" +
                    " /_____\\      \n"
    };

    public static final String[] ANIMACAO_ORC_ATAQUE = {
            "   .---.      \n" +
                    "  / o_o \\     \n" +
                    "  | >U< | />  \n" +
                    " /(|_|_|)//   \n" +
                    "  / | | //    \n" +
                    "    | |       \n" +
                    "   /   \\      \n" +
                    "  /_____\\     \n",

            "   .---.      \n" +
                    "  / >_< \\  /| \n" +
                    "  |  U  | / | \n" +
                    " /(|_|_|)/  | \n" +
                    "  / | |      \n" +
                    "    | |      \n" +
                    "   /   \\     \n" +
                    "  /_____\\    \n",

            "   .---.      \n" +
                    "  / >_< \\     \n" +
                    "  |  U  | ==> \n" +
                    " /(|_|_|)     \n" +
                    "  / | |       \n" +
                    "    | |       \n" +
                    "   /   \\      \n" +
                    "  /_____\\     \n"
    };

    public static final String[] ANIMACAO_GOBLIN_ATAQUE = {
            "             \n" +
                    "   ^   ^      \n" +
                    "  ( o_o ) />  \n" +
                    "  (| | |)//   \n" +
                    "   | | |/     \n" +
                    "    | |       \n" +
                    "   /   \\      \n" +
                    "  /_____\\     \n",

            "             \n" +
                    "   ^   ^      \n" +
                    "  ( >_< )  /| \n" +
                    "  (| | |) / | \n" +
                    "   | | |  /   \n" +
                    "    | |       \n" +
                    "   /   \\      \n" +
                    "  /_____\\     \n",

            "             \n" +
                    "   ^   ^      \n" +
                    "  ( >_< ) ==> \n" +
                    "  (| | |)     \n" +
                    "   | | |      \n" +
                    "    | |       \n" +
                    "   /   \\      \n" +
                    "  /_____\\     \n"
    };

    public static final String[] ANIMACAO_DRAGAO_SOPRO = {
            "   /\\____/\\   \n" +
                    "  /  O  O  \\  \n" +
                    " |   >__<   | \n" +
                    " /> /    \\ <\\\n" +
                    " |/ |    | \\|\n" +
                    "    |    |    \n" +
                    "   /      \\   \n" +
                    "  /________\\  \n",

            "   /\\____/\\   \n" +
                    "  /  O  O  \\  \n" +
                    " |   ----   | \n" +
                    " /> /    \\ <\\\n" +
                    " |/ |    | \\|\n" +
                    "    |    |    \n" +
                    "   /      \\   \n" +
                    "  /________\\  \n",

            "   /\\____/\\   \n" +
                    "  /  O  O  \\  \n" +
                    " |   >__<   |~~~\n" +
                    " /> /    \\ <\\~~~\n" +
                    " |/ |    | \\|~~~\n" +
                    "    |    |      \n" +
                    "   /      \\     \n" +
                    "  /________\\    \n"
    };

    public static final String[] ANIMACAO_CHEFE_IA = AnimacoesChefes.IA_SOBRECARGA;
    public static final String[] ANIMACAO_CHEFE_PROVA = AnimacoesChefes.PROVA_CORRECAO;
    public static final String[] ANIMACAO_CHEFE_MELANCIA = AnimacoesChefes.MELANCIA_IMPACTO;
    public static final String[] ANIMACAO_CHEFE_VERDADE = AnimacoesChefes.VERDADE_OLHAR;
    public static final String[] ANIMACAO_CHEFE_TROMPETE = AnimacoesChefes.TROMPETE_FANFARRA;
    public static final String[] ANIMACAO_CHEFE_REI_DEMONIO = AnimacoesChefes.REI_DEMONIO_CHAMAS;

    public static void limparConsole() {
        try {
            System.out.print("\033[H\033[2J\033[3J");
            System.out.flush();
        } catch (Exception e) {
            for (int i = 0; i < 40; i++) {
                System.out.println();
            }
        }
    }

    public static void rodarAnimacao(String[] quadros, int tempoEsperaMs) {
        rodarAnimacao(quadros, tempoEsperaMs, null);
    }

    public static void rodarAnimacao(String[] quadros, int tempoEsperaMs, String titulo) {
        if (quadros == null || quadros.length == 0) {
            return;
        }

        for (int i = 0; i < quadros.length; i++) {
            limparConsole();
            if (titulo != null && !titulo.trim().isEmpty()) {
                System.out.println(ConsoleUI.WHITE_BOLD + titulo + ConsoleUI.RESET);
                System.out.println(ConsoleUI.CYAN + "----------------------------------------" + ConsoleUI.RESET);
            }
            System.out.println(quadros[i]);

            try {
                Thread.sleep(Math.max(30, tempoEsperaMs));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public static void rodarAnimacaoDuelo(
            TipoPersonagem atacante,
            TipoPersonagem alvo,
            TipoAcaoAnimada acao,
            String titulo) {
        boolean atacanteNoLadoDireito = ehInimigo(atacante);

        if (acao != TipoAcaoAnimada.ATAQUE && acao != TipoAcaoAnimada.HABILIDADE) {
            rodarAnimacao(obterAnimacao(atacante, acao), obterTempo(acao), titulo);
            return;
        }

        if (ehChefeFinal(atacante)) {
            String[] assinatura = obterAnimacao(atacante, acao);
            rodarAnimacao(assinatura, Math.max(95, obterTempo(acao) - 35), titulo + " | Canalizando");
        }

        if (ehAtaqueADistancia(atacante)) {
            rodarAnimacaoProjetil(atacante, alvo, acao, titulo, atacanteNoLadoDireito);
            return;
        }

        rodarAnimacaoCorpoACorpo(atacante, alvo, acao, titulo, atacanteNoLadoDireito);
    }

    public static int obterTempo(TipoAcaoAnimada acao) {
        switch (acao) {
            case ATAQUE:
                return 150;
            case HABILIDADE:
                return 170;
            case DEFESA:
                return 140;
            case RECUPERAR:
                return 160;
            case ITEM:
                return 120;
            default:
                return 150;
        }
    }

    public static String[] obterAnimacao(TipoPersonagem tipo, TipoAcaoAnimada acao) {
        switch (acao) {
            case ATAQUE:
                return obterAtaque(tipo);
            case HABILIDADE:
                return obterHabilidade(tipo);
            case DEFESA:
                return gerarAnimacaoEfeito(obterBase(tipo), "[DEFENDENDO]", "[BLOQUEIO ATIVO]");
            case RECUPERAR:
                return gerarAnimacaoEfeito(obterBase(tipo), "[RECUPERANDO]", "[VITALIDADE +]");
            case ITEM:
                return gerarAnimacaoEfeito(obterBase(tipo), "[USANDO ITEM]", "[EFEITO APLICADO]");
            default:
                return new String[] { obterBase(tipo) };
        }
    }

    private static String[] obterAtaque(TipoPersonagem tipo) {
        switch (tipo) {
            case GUERREIRO:
                return ANIMACAO_GUERREIRO_ATAQUE;
            case MAGO:
                return ANIMACAO_MAGO_CONJURANDO;
            case ARQUEIRO:
                return ANIMACAO_ARQUEIRO_ATAQUE;
            case ORC:
                return ANIMACAO_ORC_ATAQUE;
            case GOBLIN:
                return ANIMACAO_GOBLIN_ATAQUE;
            case DRAGAO:
                return ANIMACAO_DRAGAO_SOPRO;
            case CHEFE_IA:
                return ANIMACAO_CHEFE_IA;
            case CHEFE_PROVA:
                return ANIMACAO_CHEFE_PROVA;
            case CHEFE_MELANCIA:
                return ANIMACAO_CHEFE_MELANCIA;
            case CHEFE_VERDADE:
                return ANIMACAO_CHEFE_VERDADE;
            case CHEFE_TROMPETE:
                return ANIMACAO_CHEFE_TROMPETE;
            case CHEFE_REI_DEMONIO:
                return ANIMACAO_CHEFE_REI_DEMONIO;
            default:
                return new String[] { obterBase(tipo) };
        }
    }

    private static String[] obterHabilidade(TipoPersonagem tipo) {
        switch (tipo) {
            case GUERREIRO:
                return gerarAnimacaoEfeito(ANIMACAO_GUERREIRO_ATAQUE[2], "[FURIA]", "[IMPACTO CRITICO]");
            case MAGO:
                return ANIMACAO_MAGO_CONJURANDO;
            case ARQUEIRO:
                return gerarAnimacaoEfeito(ANIMACAO_ARQUEIRO_ATAQUE[2], "[FOCO MAXIMO]", "[DISPARO ESPECIAL]");
            case ORC:
                return gerarAnimacaoEfeito(ANIMACAO_ORC_ATAQUE[2], "[FURIA ORC]", "[GOLPE BRUTO]");
            case GOBLIN:
                return gerarAnimacaoEfeito(ANIMACAO_GOBLIN_ATAQUE[2], "[ATAQUE SUJO]", "[GOLPE VELOZ]");
            case DRAGAO:
                return ANIMACAO_DRAGAO_SOPRO;
            case CHEFE_IA:
                return ANIMACAO_CHEFE_IA;
            case CHEFE_PROVA:
                return ANIMACAO_CHEFE_PROVA;
            case CHEFE_MELANCIA:
                return ANIMACAO_CHEFE_MELANCIA;
            case CHEFE_VERDADE:
                return ANIMACAO_CHEFE_VERDADE;
            case CHEFE_TROMPETE:
                return ANIMACAO_CHEFE_TROMPETE;
            case CHEFE_REI_DEMONIO:
                return ANIMACAO_CHEFE_REI_DEMONIO;
            default:
                return new String[] { obterBase(tipo) };
        }
    }

    private static boolean ehChefeFinal(TipoPersonagem tipo) {
        return tipo == TipoPersonagem.CHEFE_IA
                || tipo == TipoPersonagem.CHEFE_PROVA
                || tipo == TipoPersonagem.CHEFE_MELANCIA
                || tipo == TipoPersonagem.CHEFE_VERDADE
                || tipo == TipoPersonagem.CHEFE_TROMPETE
                || tipo == TipoPersonagem.CHEFE_REI_DEMONIO;
    }

    private static void rodarAnimacaoCorpoACorpo(
            TipoPersonagem atacante,
            TipoPersonagem alvo,
            TipoAcaoAnimada acao,
            String titulo,
            boolean atacanteNoLadoDireito) {
        boolean alvoNoLadoDireito = !atacanteNoLadoDireito;
        int colunaAtacanteBase = atacanteNoLadoDireito ? COLUNA_DIREITA : COLUNA_ESQUERDA;
        int colunaAlvoBase = alvoNoLadoDireito ? COLUNA_DIREITA : COLUNA_ESQUERDA;
        int direcao = atacanteNoLadoDireito ? -1 : 1;

        String arteBaseAtacante = orientarParaLado(obterBase(atacante), atacanteNoLadoDireito);
        String arteImpactoAtacante = orientarParaLado(obterQuadroImpacto(atacante), atacanteNoLadoDireito);
        String arteAlvo = orientarParaLado(obterBase(alvo), alvoNoLadoDireito);

        int larguraAtacante = larguraMaxima(normalizarArte(arteImpactoAtacante));
        int larguraAlvo = larguraMaxima(normalizarArte(arteAlvo));
        int deslocamentoContato = calcularDeslocamentoContato(colunaAtacanteBase, colunaAlvoBase, larguraAtacante);
        int[] deslocamentos = criarSequenciaInvestida(deslocamentoContato);
        int tempo = Math.max(65, obterTempo(acao) - 25);

        for (int i = 0; i < deslocamentos.length; i++) {
            int deslocamentoBase = deslocamentos[i];
            int deslocamentoReal = deslocamentoBase * direcao;
            boolean emContato = deslocamentoBase >= deslocamentoContato - 2;
            String arteAtacante = emContato ? arteImpactoAtacante : arteBaseAtacante;

            String frame = montarFrameDuelo(
                    arteAtacante,
                    colunaAtacanteBase,
                    arteAlvo,
                    colunaAlvoBase,
                    deslocamentoReal,
                    0,
                    -1,
                    "",
                    false,
                    "",
                    0);
            exibirFrameComTitulo(titulo, frame);
            dormir(tempo);
        }

        int[] recuosAlvo = { 2 * direcao, 4 * direcao, 2 * direcao, 0 };
        String[] textosImpacto = { "IMPACTO", "CLANG", "", "" };
        for (int i = 0; i < recuosAlvo.length; i++) {
            int deslocamentoAlvo = recuosAlvo[i];
            int deslocamentoAtacante = (deslocamentoContato - (i > 1 ? 2 : 0)) * direcao;
            int colunaImpacto = calcularColunaImpacto(
                    colunaAlvoBase + deslocamentoAlvo,
                    larguraAlvo,
                    alvoNoLadoDireito);

            String frameImpacto = montarFrameDuelo(
                    arteImpactoAtacante,
                    colunaAtacanteBase,
                    arteAlvo,
                    colunaAlvoBase,
                    deslocamentoAtacante,
                    deslocamentoAlvo,
                    -1,
                    "",
                    i < 2,
                    textosImpacto[i],
                    colunaImpacto);
            exibirFrameComTitulo(titulo, frameImpacto);
            dormir(85);
        }
    }

    private static void rodarAnimacaoProjetil(
            TipoPersonagem atacante,
            TipoPersonagem alvo,
            TipoAcaoAnimada acao,
            String titulo,
            boolean atacanteNoLadoDireito) {
        boolean alvoNoLadoDireito = !atacanteNoLadoDireito;
        int colunaAtacanteBase = atacanteNoLadoDireito ? COLUNA_DIREITA : COLUNA_ESQUERDA;
        int colunaAlvoBase = alvoNoLadoDireito ? COLUNA_DIREITA : COLUNA_ESQUERDA;
        int direcao = atacanteNoLadoDireito ? -1 : 1;

        String arteAtacante = orientarParaLado(obterQuadroDisparo(atacante), atacanteNoLadoDireito);
        String arteAlvo = orientarParaLado(obterBase(alvo), alvoNoLadoDireito);
        String[] linhasAtacante = normalizarArte(arteAtacante);
        String[] linhasAlvo = normalizarArte(arteAlvo);
        int larguraAtacante = larguraMaxima(linhasAtacante);
        int larguraAlvo = larguraMaxima(linhasAlvo);

        int inicioProjetil;
        int fimProjetil;
        if (direcao > 0) {
            inicioProjetil = colunaAtacanteBase + larguraAtacante + 1;
            fimProjetil = colunaAlvoBase - 2;
        } else {
            inicioProjetil = colunaAtacanteBase - 2;
            fimProjetil = colunaAlvoBase + larguraAlvo + 1;
        }

        int passo = 4;
        int tempo = Math.max(75, obterTempo(acao) - 10);
        String projetil = obterProjetil(atacante, acao, direcao);

        for (int pos = inicioProjetil; direcao > 0 ? pos <= fimProjetil : pos >= fimProjetil; pos += passo * direcao) {
            String frame = montarFrameDuelo(
                    arteAtacante,
                    colunaAtacanteBase,
                    arteAlvo,
                    colunaAlvoBase,
                    0,
                    0,
                    pos,
                    projetil,
                    false,
                    "",
                    0);
            exibirFrameComTitulo(titulo, frame);
            dormir(tempo);
        }

        int[] recuosAlvo = { 0, 2 * direcao, 1 * direcao };
        String[] textosImpacto = { "ACERTO", "IMPACTO", "" };
        for (int i = 0; i < recuosAlvo.length; i++) {
            int deslocamentoAlvo = recuosAlvo[i];
            int colunaImpacto = calcularColunaImpacto(
                    colunaAlvoBase + deslocamentoAlvo,
                    larguraAlvo,
                    alvoNoLadoDireito);

            String frameImpacto = montarFrameDuelo(
                    arteAtacante,
                    colunaAtacanteBase,
                    arteAlvo,
                    colunaAlvoBase,
                    0,
                    deslocamentoAlvo,
                    fimProjetil,
                    projetil,
                    i < 2,
                    textosImpacto[i],
                    colunaImpacto);
            exibirFrameComTitulo(titulo, frameImpacto);
            dormir(90);
        }
    }

    private static String montarFrameDuelo(
            String arteAtacante,
            int colunaAtacanteBase,
            String arteAlvo,
            int colunaAlvoBase,
            int deslocamentoAtacante,
            int deslocamentoAlvo,
            int posicaoProjetil,
            String simboloProjetil,
            boolean impacto,
            String textoImpacto,
            int colunaImpacto) {
        String[] linhasAtacante = normalizarArte(arteAtacante);
        String[] linhasAlvo = normalizarArte(arteAlvo);
        int linhasTotais = Math.max(linhasAtacante.length, linhasAlvo.length);
        int linhaProjetil = Math.max(1, linhasTotais / 2);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < linhasTotais; i++) {
            char[] linha = criarLinhaVazia();
            if (i < linhasAtacante.length) {
                escreverTexto(linha, colunaAtacanteBase + deslocamentoAtacante, linhasAtacante[i]);
            }
            if (i < linhasAlvo.length) {
                escreverTexto(linha, colunaAlvoBase + deslocamentoAlvo, linhasAlvo[i]);
            }

            if (posicaoProjetil >= 0 && i == linhaProjetil) {
                escreverTexto(linha, posicaoProjetil, simboloProjetil);
            }

            if (impacto && i == linhaProjetil) {
                String efeito = "*** " + textoImpacto + " ***";
                int colunaEfeito = Math.max(0, Math.min(colunaImpacto, LARGURA_CANVAS - efeito.length()));
                escreverTexto(linha, colunaEfeito, efeito);
            }

            sb.append(new String(linha)).append('\n');
        }

        return sb.toString();
    }

    private static void exibirFrameComTitulo(String titulo, String frame) {
        limparConsole();
        if (titulo != null && !titulo.trim().isEmpty()) {
            System.out.println(ConsoleUI.WHITE_BOLD + titulo + ConsoleUI.RESET);
            System.out.println(ConsoleUI.CYAN + "----------------------------------------" + ConsoleUI.RESET);
        }
        System.out.println(frame);
    }

    private static boolean ehAtaqueADistancia(TipoPersonagem tipo) {
        return tipo == TipoPersonagem.ARQUEIRO
                || tipo == TipoPersonagem.MAGO
                || tipo == TipoPersonagem.DRAGAO
                || tipo == TipoPersonagem.CHEFE_IA
                || tipo == TipoPersonagem.CHEFE_PROVA
                || tipo == TipoPersonagem.CHEFE_VERDADE
                || tipo == TipoPersonagem.CHEFE_TROMPETE;
    }

    private static boolean ehInimigo(TipoPersonagem tipo) {
        return tipo == TipoPersonagem.ORC
                || tipo == TipoPersonagem.GOBLIN
                || tipo == TipoPersonagem.DRAGAO
                || tipo == TipoPersonagem.CHEFE_IA
                || tipo == TipoPersonagem.CHEFE_PROVA
                || tipo == TipoPersonagem.CHEFE_MELANCIA
                || tipo == TipoPersonagem.CHEFE_VERDADE
                || tipo == TipoPersonagem.CHEFE_TROMPETE
                || tipo == TipoPersonagem.CHEFE_REI_DEMONIO;
    }

    private static String obterProjetil(TipoPersonagem atacante, TipoAcaoAnimada acao, int direcao) {
        if (atacante == TipoPersonagem.ARQUEIRO) {
            return direcao > 0 ? "--->" : "<---";
        }
        if (atacante == TipoPersonagem.MAGO) {
            return direcao > 0
                    ? (acao == TipoAcaoAnimada.HABILIDADE ? "~*~*>" : "~*>")
                    : (acao == TipoAcaoAnimada.HABILIDADE ? "<*~*~" : "<*~");
        }
        if (atacante == TipoPersonagem.DRAGAO) {
            return direcao > 0 ? "~~~~>" : "<~~~~";
        }
        if (atacante == TipoPersonagem.CHEFE_IA) {
            return direcao > 0 ? "1010>" : "<0101";
        }
        if (atacante == TipoPersonagem.CHEFE_PROVA) {
            return direcao > 0 ? "X=>" : "<=X";
        }
        if (atacante == TipoPersonagem.CHEFE_VERDADE) {
            return direcao > 0 ? "oOo>" : "<oOo";
        }
        if (atacante == TipoPersonagem.CHEFE_TROMPETE) {
            return direcao > 0 ? ")))>" : "<(((";
        }
        return direcao > 0 ? "==>" : "<==";
    }

    private static String obterQuadroImpacto(TipoPersonagem atacante) {
        String[] ataque = obterAtaque(atacante);
        if (ataque != null && ataque.length > 0) {
            return ataque[Math.min(2, ataque.length - 1)];
        }
        return obterBase(atacante);
    }

    private static String obterQuadroDisparo(TipoPersonagem atacante) {
        String[] ataque = obterAtaque(atacante);
        if (ataque != null && ataque.length > 0) {
            return ataque[Math.min(1, ataque.length - 1)];
        }
        return obterBase(atacante);
    }

    private static String[] normalizarArte(String arte) {
        if (arte == null) {
            return new String[] { "(sem arte)" };
        }

        String[] linhasBrutas = arte.split("\\r?\\n");
        ArrayList<String> linhas = new ArrayList<String>();
        int menorRecuo = Integer.MAX_VALUE;

        for (int i = 0; i < linhasBrutas.length; i++) {
            String linha = linhasBrutas[i];
            if (!linha.trim().isEmpty()) {
                menorRecuo = Math.min(menorRecuo, contarRecuo(linha));
                linhas.add(linha);
            }
        }

        if (linhas.isEmpty()) {
            return new String[] { "(sem arte)" };
        }

        if (menorRecuo > 0 && menorRecuo != Integer.MAX_VALUE) {
            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                int inicio = Math.min(menorRecuo, linha.length());
                linhas.set(i, linha.substring(inicio));
            }
        }

        return linhas.toArray(new String[0]);
    }

    private static int larguraMaxima(String[] linhas) {
        int largura = 0;
        for (int i = 0; i < linhas.length; i++) {
            if (linhas[i].length() > largura) {
                largura = linhas[i].length();
            }
        }
        return largura;
    }

    private static char[] criarLinhaVazia() {
        char[] linha = new char[LARGURA_CANVAS];
        for (int i = 0; i < linha.length; i++) {
            linha[i] = ' ';
        }
        return linha;
    }

    private static void escreverTexto(char[] destino, int colunaInicial, String texto) {
        if (texto == null) {
            return;
        }

        int coluna = Math.max(0, colunaInicial);
        for (int i = 0; i < texto.length() && coluna + i < destino.length; i++) {
            destino[coluna + i] = texto.charAt(i);
        }
    }

    private static void dormir(int ms) {
        try {
            Thread.sleep(Math.max(30, ms));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static int contarRecuo(String linha) {
        int recuo = 0;
        while (recuo < linha.length() && linha.charAt(recuo) == ' ') {
            recuo++;
        }
        return recuo;
    }

    private static int calcularDeslocamentoContato(int colunaAtacanteBase, int colunaAlvoBase, int larguraAtacante) {
        int distancia = Math.abs(colunaAlvoBase - colunaAtacanteBase);
        int contato = distancia - Math.max(4, larguraAtacante - 1) - 1;
        return Math.max(16, contato);
    }

    private static int[] criarSequenciaInvestida(int deslocamentoContato) {
        int t1 = Math.max(2, deslocamentoContato / 6);
        int t2 = Math.max(t1 + 2, deslocamentoContato / 3);
        int t3 = Math.max(t2 + 2, (deslocamentoContato * 2) / 3);
        int t4 = Math.max(t3 + 2, deslocamentoContato - 3);

        return new int[] {
                0,
                -2,
                t1,
                t2,
                t3,
                t4,
                deslocamentoContato,
                deslocamentoContato - 2,
                t3,
                t2,
                t1,
                0
        };
    }

    private static int calcularColunaImpacto(int colunaAlvo, int larguraAlvo, boolean alvoNoLadoDireito) {
        if (alvoNoLadoDireito) {
            return colunaAlvo - 6;
        }
        return colunaAlvo + Math.max(2, larguraAlvo - 2);
    }

    private static String orientarParaLado(String arte, boolean ladoDireito) {
        if (arte == null || arte.trim().isEmpty()) {
            return arte;
        }
        return ladoDireito ? espelharArte(arte) : arte;
    }

    private static String espelharArte(String arte) {
        String[] linhas = arte.split("\\r?\\n", -1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < linhas.length; i++) {
            if (i > 0) {
                sb.append('\n');
            }
            sb.append(espelharLinha(linhas[i]));
        }
        return sb.toString();
    }

    private static String espelharLinha(String linha) {
        StringBuilder espelhada = new StringBuilder(linha.length());
        for (int i = linha.length() - 1; i >= 0; i--) {
            espelhada.append(espelharCaractere(linha.charAt(i)));
        }
        return espelhada.toString();
    }

    private static char espelharCaractere(char caractere) {
        switch (caractere) {
            case '/':
                return '\\';
            case '\\':
                return '/';
            case '(':
                return ')';
            case ')':
                return '(';
            case '[':
                return ']';
            case ']':
                return '[';
            case '{':
                return '}';
            case '}':
                return '{';
            case '<':
                return '>';
            case '>':
                return '<';
            default:
                return caractere;
        }
    }

    private static String obterBase(TipoPersonagem tipo) {
        switch (tipo) {
            case GUERREIRO:
                return AsciiArt.GUERREIRO;
            case MAGO:
                return AsciiArt.MAGO;
            case ARQUEIRO:
                return AsciiArt.ARQUEIRO;
            case ORC:
                return AsciiArt.ORC;
            case GOBLIN:
                return AsciiArt.GOBLIN;
            case DRAGAO:
                return AsciiArt.DRAGAO;
            case CHEFE_IA:
                return AsciiArt.CHEFE_IA;
            case CHEFE_PROVA:
                return AsciiArt.CHEFE_PROVA;
            case CHEFE_MELANCIA:
                return AsciiArt.CHEFE_MELANCIA;
            case CHEFE_VERDADE:
                return AsciiArt.CHEFE_VERDADE;
            case CHEFE_TROMPETE:
                return AsciiArt.CHEFE_TROMPETE;
            case CHEFE_REI_DEMONIO:
                return AsciiArt.CHEFE_REI_DEMONIO;
            default:
                return AsciiArt.GUERREIRO;
        }
    }

    private static String[] gerarAnimacaoEfeito(String quadroBase, String etapa1, String etapa2) {
        String base = quadroBase == null ? "" : quadroBase;
        return new String[] {
                base + "\n   " + etapa1 + "\n",
                base + "\n   " + etapa2 + "\n"
        };
    }

    public static void main(String[] args) {
        System.out.println("Teste de animacoes do RPG");
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        rodarAnimacaoDuelo(TipoPersonagem.GUERREIRO, TipoPersonagem.ORC, TipoAcaoAnimada.ATAQUE,
                "Guerreiro atacando Orc");
        rodarAnimacaoDuelo(TipoPersonagem.ARQUEIRO, TipoPersonagem.GOBLIN, TipoAcaoAnimada.ATAQUE,
                "Arqueiro acertando Goblin");
        rodarAnimacaoDuelo(TipoPersonagem.MAGO, TipoPersonagem.DRAGAO, TipoAcaoAnimada.HABILIDADE,
                "Mago conjurando no Dragao");

        rodarAnimacao(obterAnimacao(TipoPersonagem.CHEFE_IA, TipoAcaoAnimada.ATAQUE), 130,
            "Chefe IA - Ataque");
        rodarAnimacao(obterAnimacao(TipoPersonagem.CHEFE_PROVA, TipoAcaoAnimada.ATAQUE), 130,
            "Chefe Prova - Ataque");
        rodarAnimacao(obterAnimacao(TipoPersonagem.CHEFE_MELANCIA, TipoAcaoAnimada.ATAQUE), 130,
            "Chefe Melancia - Ataque");
        rodarAnimacao(obterAnimacao(TipoPersonagem.CHEFE_VERDADE, TipoAcaoAnimada.ATAQUE), 130,
            "Chefe Verdade - Ataque");
        rodarAnimacao(obterAnimacao(TipoPersonagem.CHEFE_TROMPETE, TipoAcaoAnimada.ATAQUE), 130,
            "Chefe Trompete - Ataque");
        rodarAnimacao(obterAnimacao(TipoPersonagem.CHEFE_REI_DEMONIO, TipoAcaoAnimada.ATAQUE), 130,
            "Chefe Rei Demonio - Ataque");
    }
}
