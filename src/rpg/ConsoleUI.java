package rpg;

import java.util.Scanner;

public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int BARRA_HP_LARGURA = 20;

    public static final int CAIXA_LARGURA = 58;

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void limparTela() {
        clearScreen();
    }

    public static String centralizarTexto(String texto, int largura) {
        return centerText(texto, largura);
    }

    public static void animarTexto(String texto, int delayMillis) {
        for (char c : texto.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            pause(delayMillis);
        }
        System.out.println();
    }

    public static void desenharCaixa(String titulo, String[] conteudo, int largura) {
        desenharCaixa(titulo, conteudo, largura, ANSIColors.WHITE);
    }

    public static void desenharCaixa(String titulo, String[] conteudo, int largura, String cor) {
        String borda = repeat('─', largura);
        String estilo = cor == null ? "" : cor + ANSIColors.BOLD;
        String reset = cor == null ? "" : ANSIColors.RESET;

        System.out.println(estilo + "┌" + borda + "┐" + reset);
        if (titulo != null && !titulo.isEmpty()) {
            System.out.println(estilo + "│" + centralizarTexto(titulo, largura) + "│" + reset);
            System.out.println(estilo + "├" + borda + "┤" + reset);
        }
        for (String linha : conteudo) {
            System.out.println(estilo + "│" + centralizarTexto(linha, largura) + "│" + reset);
        }
        System.out.println(estilo + "└" + borda + "┘" + reset);
    }

    public static void mostrarLogoEmpresa() {
        limparTela();
        String title = "███████╗    ██████╗    ███████╗      ██████╗ ██████╗ ███╗   ███╗██████╗ ██╗   ██╗████████╗ █████╗  ██████╗ █████╗  ██████╗ \n" +
                "██╔════╝   ██╔═══██╗   ██╔════╝     ██╔════╝██╔═══██╗████╗ ████║██╔══██╗██║   ██║╚══██╔══╝██╔══██╗██╔════╝██╔══██╗██╔═══██╗\n" +
                "███████╗   ██║   ██║   ███████╗     ██║     ██║   ██║██╔████╔██║██████╔╝██║   ██║   ██║   ███████║██║     ███████║██║   ██║\n" +
                "╚════██║   ██║   ██║   ╚════██║     ██║     ██║   ██║██║╚██╔╝██║██╔═══╝ ██║   ██║   ██║   ██╔══██║██║     ██╔══██║██║   ██║\n" +
                "███████║   ╚██████╔╝   ███████║     ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║     ╚██████╔╝   ██║   ██║  ██║╚██████╗██║  ██║╚██████╔╝\n" +
                "╚══════╝    ╚═════╝    ╚══════╝      ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝      ╚═════╝    ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ ╚═════╝ \n";

        System.out.println(ANSIColors.CYAN + ANSIColors.BOLD);
        Animacoes.typeWriter(title, 12);
        System.out.println(ANSIColors.RESET);

        pause(Animacoes.DELAY_MEDIO);

        for (int fade = 0; fade < 3; fade++) {
            limparTela();
            String cor = fade % 2 == 0 ? ANSIColors.CYAN : ANSIColors.WHITE;
            System.out.println(cor + ANSIColors.BOLD + title + ANSIColors.RESET);
            pause(Animacoes.DELAY_CURTO / 2);
        }
        limparTela();
    }

    public static void mostrarBemVindo() {
        limparTela();
        String[] linhas = {"Bem-vindo ao", "RPG Clássico", "Sua aventura começa agora."};
        desenharCaixa("BEM-VINDO", linhas, CAIXA_LARGURA, ANSIColors.YELLOW);
        System.out.println();
        animarTexto(ANSIColors.WHITE + "Prepare-se para a jornada..." + ANSIColors.RESET, 40);
        pause(Animacoes.DELAY_MEDIO);
    }

    public static void mostrarCarregamento() {
        limparTela();
        String[] linhas = {"Ajustando equipamentos", "Preparando a próxima batalha"};
        desenharCaixa("CARREGAMENTO", linhas, CAIXA_LARGURA, ANSIColors.BLUE);
        System.out.println();
        Animacoes.loadingBar("Carregando", 24, 50);
        pause(Animacoes.DELAY_MEDIO);
    }

    private static void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }


    public static void mostrarMenu(String[] opcoes) {
        mostrarMenu("Selecione uma ação:", opcoes);
    }

    public static void mostrarMenu(String titulo, String[] opcoes) {
        System.out.println(ANSIColors.WHITE + titulo + ANSIColors.RESET);
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println(ANSIColors.YELLOW + (i + 1) + " - " + opcoes[i] + ANSIColors.RESET);
        }
    }

    public static int lerOpcao(int quantidade) {
        while (true) {
            System.out.print(ANSIColors.WHITE + "Digite o número da opção e pressione ENTER: " + ANSIColors.RESET);
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();
                if (escolha >= 1 && escolha <= quantidade) {
                    return escolha - 1;
                }
            } catch (Exception e) {
                scanner.nextLine();
            }
            System.out.println(ANSIColors.RED + "Entrada inválida. Digite um número válido." + ANSIColors.RESET);
        }
    }

    public static void renderTela(PersonagemBase jogador, Inimigo inimigo, String[] inimigoSprite, String[] jogadorSprite, String titulo, String dialogo) {
        limparTela();
        if (titulo != null && !titulo.isEmpty()) {
            showTitle(titulo);
        }
        showEnemyHUD(inimigo);
        showSprite(inimigoSprite, 8);
        System.out.println();
        showSprite(jogadorSprite, 8);
        showPlayerHUD(jogador);
        System.out.println();
        if (dialogo != null) {
            showDialog(dialogo);
        }
    }

    private static void showTitle(String titulo) {
        System.out.println(ANSIColors.GREEN + ANSIColors.BOLD + centerText("== " + titulo + " ==", CAIXA_LARGURA) + ANSIColors.RESET);
        System.out.println();
    }

    private static void showEnemyHUD(Inimigo inimigo) {
        String[] linhas = {
                "Inimigo Lv " + inimigo.getNivel() + " - " + inimigo.getNome(),
                formatStat("HP:", inimigo.getVida(), inimigo.getVidaMaxima(), BARRA_HP_LARGURA, getHealthColor(inimigo.getVida(), inimigo.getVidaMaxima()))
        };
        desenharCaixa("INIMIGO", linhas, CAIXA_LARGURA, ANSIColors.RED);
    }

    private static void showPlayerHUD(PersonagemBase jogador) {
        String[] linhas = {
                "Jogador Lv " + jogador.getNivel() + " - " + jogador.getNome(),
                "XP: " + jogador.getExperiencia() + " / " + jogador.getXpParaSubir(),
                "Ouro: " + jogador.getOuro(),
                formatStat("HP:", jogador.getVida(), jogador.getVidaMaxima(), BARRA_HP_LARGURA, getHealthColor(jogador.getVida(), jogador.getVidaMaxima())),
                formatStat("EN:", jogador.getEnergia(), jogador.getEnergiaMaxima(), BARRA_HP_LARGURA, getEnergyColor(jogador.getEnergia(), jogador.getEnergiaMaxima()))
        };
        desenharCaixa("JOGADOR", linhas, CAIXA_LARGURA, ANSIColors.CYAN);
    }

    private static void showSprite(String[] sprite, int padding) {
        for (String linha : sprite) {
            System.out.printf("%s%" + padding + "s%s%n", "", centerText(linha, CAIXA_LARGURA), "");
        }
    }

    private static void showDialog(String dialogo) {
        desenharCaixa(null, new String[]{dialogo}, CAIXA_LARGURA, ANSIColors.WHITE);
    }

    public static void printEffect(String efeito, String cor) {
        System.out.println(cor + ANSIColors.BOLD + efeito + ANSIColors.RESET);
    }

    public static void pressEnterToContinue() {
        System.out.print(ANSIColors.WHITE + "Pressione ENTER para continuar..." + ANSIColors.RESET);
        try {
            scanner.nextLine();
        } catch (Exception ignored) {
        }
    }

    public static String lerTexto(String prompt) {
        flushInputBuffer();
        System.out.print(ANSIColors.WHITE + prompt + ANSIColors.RESET);
        try {
            String valor = scanner.nextLine().trim();
            return valor.isEmpty() ? "Herói" : valor;
        } catch (Exception e) {
            return "Herói";
        }
    }

    private static String formatStat(String label, int atual, int maximo, int tamanho, String cor) {
        return label + " " + getBar(atual, maximo, tamanho, cor) + " " + atual + "/" + maximo;
    }

    private static void flushInputBuffer() {
        try {
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception ignored) {
        }
    }

    private static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int totalPadding = width - text.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        return repeat(' ', leftPadding) + text + repeat(' ', rightPadding);
    }

    private static String repeat(char c, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(c);
        }
        return builder.toString();
    }

    private static String getBar(int atual, int maximo, int tamanho, String cor) {
        int preenchido = (int) Math.round(((double) atual / maximo) * tamanho);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            bar.append(i < preenchido ? "█" : "░");
        }
        return cor + bar + ANSIColors.RESET;
    }

    private static String getHealthColor(int atual, int maximo) {
        double percentual = (double) atual / maximo;
        if (percentual > 0.6) {
            return ANSIColors.GREEN;
        } else if (percentual > 0.3) {
            return ANSIColors.YELLOW;
        } else {
            return ANSIColors.RED;
        }
    }

    private static String getEnergyColor(int atual, int maximo) {
        double percentual = (double) atual / maximo;
        if (percentual > 0.6) {
            return ANSIColors.CYAN;
        } else if (percentual > 0.3) {
            return ANSIColors.BLUE;
        } else {
            return ANSIColors.YELLOW;
        }
    }
}
