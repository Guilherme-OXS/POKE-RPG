package rpg.utils;

import java.util.ArrayList;
import java.util.List;

import rpg.model.PersonagemBase;
import rpg.model.itens.Item;

public final class ConsoleUI {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE_BOLD = "\u001B[1;37m";

    private static final int LARGURA_COLUNA_ARTE = 36;
    private static final int TAMANHO_BARRA = 16;
    private static final int LIMITE_HISTORICO = 3;

    private ConsoleUI() {
    }

    public static void limparTela() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }

    public static void pausar(int milissegundos) {
        try {
            Thread.sleep(Math.max(0, milissegundos));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void digitarTexto(String texto, int atrasoMs) {
        if (texto == null) {
            return;
        }

        for (char caractere : texto.toCharArray()) {
            System.out.print(caractere);
            System.out.flush();
            pausar(atrasoMs);
        }
        System.out.println();
    }

    public static String cor(String texto, String corAnsi) {
        return corAnsi + texto + RESET;
    }

    public static void mostrarBannerPrincipal() {
        System.out.println(WHITE_BOLD + "DUNGEON RPG // HEROI VS IA" + RESET);
        System.out.println(CYAN + repetir('=', 72) + RESET);
    }

    public static void mostrarSeparador() {
        System.out.println(CYAN + repetir('-', 72) + RESET);
    }

    public static void mostrarCampoBatalha(PersonagemBase heroi, PersonagemBase inimigo) {
        System.out.println(PURPLE + "Confronto: " + heroi.getNome() + " vs " + inimigo.getNome() + RESET);
        String[] heroiArte = normalizarArte(orientarParaLado(heroi.getAsciiArte(), false));
        String[] inimigoArte = normalizarArte(orientarParaLado(inimigo.getAsciiArte(), true));
        int linhas = Math.max(heroiArte.length, inimigoArte.length);

        for (int i = 0; i < linhas; i++) {
            String esquerda = i < heroiArte.length ? heroiArte[i] : "";
            String direita = i < inimigoArte.length ? inimigoArte[i] : "";
            System.out.println(" " + padDireita(esquerda, LARGURA_COLUNA_ARTE) + "   " + direita);
        }
        mostrarSeparador();
    }

    public static void mostrarStatus(PersonagemBase heroi, PersonagemBase inimigo) {
        System.out.println(WHITE_BOLD + "Status" + RESET);
        System.out.println(
                WHITE_BOLD + heroi.getNome() + RESET
                        + "  HP " + barraProgresso(heroi.getVidaAtual(), heroi.getVidaMaxima(), RED)
                        + " " + heroi.getVidaAtual() + "/" + heroi.getVidaMaxima()
                        + "  EN " + barraProgresso(heroi.getEnergiaAtual(), heroi.getEnergiaMaxima(), BLUE)
                        + " " + heroi.getEnergiaAtual() + "/" + heroi.getEnergiaMaxima()
                        + "  Ouro " + cor(String.valueOf(heroi.getOuro()), YELLOW));

        System.out.println(
                WHITE_BOLD + inimigo.getNome() + RESET
                        + "  HP " + barraProgresso(inimigo.getVidaAtual(), inimigo.getVidaMaxima(), RED)
                        + " " + inimigo.getVidaAtual() + "/" + inimigo.getVidaMaxima()
                        + "  EN " + barraProgresso(inimigo.getEnergiaAtual(), inimigo.getEnergiaMaxima(), BLUE)
                        + " " + inimigo.getEnergiaAtual() + "/" + inimigo.getEnergiaMaxima());

        mostrarSeparador();
    }

    public static void mostrarHistorico(String[] ultimasAcoes) {
        ArrayList<String> eventos = new ArrayList<String>();

        for (int i = ultimasAcoes.length - 1; i >= 0; i--) {
            if (ultimasAcoes[i] != null && eventos.size() < LIMITE_HISTORICO) {
                eventos.add(0, ultimasAcoes[i]);
            }
        }

        if (eventos.isEmpty()) {
            System.out.println(GREEN + "Eventos: sem eventos recentes." + RESET);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < eventos.size(); i++) {
            if (i > 0) {
                sb.append(" | ");
            }
            sb.append(eventos.get(i));
        }
        System.out.println(GREEN + "Eventos: " + RESET + limitar(sb.toString(), 160));
    }

    public static void mostrarPainelAcoes(String titulo, String[] opcoes) {
        System.out.println(PURPLE + titulo + RESET);
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println(" " + opcoes[i]);
        }
        mostrarSeparador();
    }

    public static void mostrarInventario(List<Item> itens) {
        System.out.println(CYAN + "Inventario" + RESET);

        if (itens == null || itens.isEmpty()) {
            System.out.println(" Sem itens disponiveis.");
            mostrarSeparador();
            return;
        }

        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            System.out.println(" " + (i + 1) + ". " + item.getNome() + " - " + item.getDescricao());
        }
        System.out.println(" 0. Cancelar");
        mostrarSeparador();
    }

    public static void mostrarLoja(PersonagemBase heroi, Item[] catalogo) {
        System.out.println(YELLOW + "Loja do Acampamento" + RESET + "  Ouro: "
                + cor(String.valueOf(heroi.getOuro()), YELLOW));
        mostrarSeparador();

        for (int i = 0; i < catalogo.length; i++) {
            Item item = catalogo[i];
            System.out.println(" " + (i + 1) + ". " + item.getNome() + " - " + item.getPreco() + " ouro");
            System.out.println("    " + item.getDescricao());
        }
        System.out.println(" 0. Sair da loja");
        mostrarSeparador();
    }

    public static void mostrarTransicaoFase(int faseAtual, int totalFases, String nomeInimigo) {
        limparTela();
        mostrarBannerPrincipal();
        System.out.println(WHITE_BOLD + "Fase " + faseAtual + " de " + totalFases + RESET);
        System.out.println(RED + "Inimigo: " + nomeInimigo + RESET);
        mostrarSeparador();
    }

    public static void mensagemInfo(String mensagem) {
        System.out.println(CYAN + "> " + mensagem + RESET);
    }

    public static void mensagemSucesso(String mensagem) {
        System.out.println(GREEN + "> " + mensagem + RESET);
    }

    public static void mensagemAlerta(String mensagem) {
        System.out.println(YELLOW + "> " + mensagem + RESET);
    }

    public static void mensagemErro(String mensagem) {
        System.out.println(RED + "> " + mensagem + RESET);
    }

    private static String barraProgresso(int atual, int maximo, String corDaBarra) {
        int maxSeguro = Math.max(1, maximo);
        int atualSeguro = Math.max(0, Math.min(atual, maxSeguro));
        double percentual = atualSeguro / (double) maxSeguro;
        int preenchido = (int) Math.round(percentual * TAMANHO_BARRA);
        preenchido = Math.max(0, Math.min(preenchido, TAMANHO_BARRA));

        String parteCheia = repetir('=', preenchido);
        String parteVazia = repetir('.', TAMANHO_BARRA - preenchido);
        return "[" + corDaBarra + parteCheia + RESET + parteVazia + "]";
    }

    private static String limitar(String texto, int tamanho) {
        if (texto == null) {
            return "";
        }
        if (texto.length() <= tamanho) {
            return texto;
        }
        return texto.substring(0, Math.max(0, tamanho - 3)) + "...";
    }

    private static String[] normalizarArte(String arte) {
        if (arte == null) {
            return new String[] {"(sem arte)"};
        }

        String[] linhasOriginais = arte.split("\\r?\\n");
        ArrayList<String> linhas = new ArrayList<String>();
        int menorRecuo = Integer.MAX_VALUE;
        for (int i = 0; i < linhasOriginais.length; i++) {
            String linha = linhasOriginais[i];
            if (!linha.trim().isEmpty()) {
                menorRecuo = Math.min(menorRecuo, contarRecuo(linha));
                linhas.add(linha);
            }
        }

        if (linhas.isEmpty()) {
            return new String[] {"(sem arte)"};
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

    private static int contarRecuo(String linha) {
        int recuo = 0;
        while (recuo < linha.length() && linha.charAt(recuo) == ' ') {
            recuo++;
        }
        return recuo;
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

    private static String padDireita(String texto, int tamanhoFinal) {
        String base = texto == null ? "" : texto;
        if (base.length() >= tamanhoFinal) {
            return base;
        }
        return base + repetir(' ', tamanhoFinal - base.length());
    }

    private static String repetir(char caractere, int quantidade) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < quantidade; i++) {
            sb.append(caractere);
        }
        return sb.toString();
    }
}
