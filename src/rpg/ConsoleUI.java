package rpg;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * Classe utilitária responsável pela interface do usuário no console.
 * 
 * Fornece métodos para:
 * - Limpar a tela do console (cross-platform)
 * - Desenhar caixas e bordas decorativas
 * - Exibir menus interativos
 * - Renderizar telas de combate com HUD e sprites
 * - Gerenciar entrada do usuário
 * - Exibir animações e efeitos visuais
 * - Mostrar logos e mensagens de boas-vindas
 * 
 * Toda interação visual do jogo passa por essa classe.
 * 
 * @author Projeto Integrador RPG
 * @see Animacoes
 * @see ANSIColors
 */
public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int BARRA_HP_LARGURA = 20;
    /** Largura padrão das caixas de diálogo */
    public static final int CAIXA_LARGURA = 58;

    /**
     * Limpa a tela do console de forma cross-platform.
     * - Windows: usa comando "cls"
     * - Linux/Mac: usa caracteres de escape ANSI
     * - Fallback: imprime 50 linhas vazias
     */
    public static void limparTela() {
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


    /**
     * Desenha uma caixa decorativa com bordas e conteúdo.
     * Versão simplificada que usa cor branca padrão.
     * 
     * @param titulo Título da caixa (opcional)
     * @param conteudo Array de strings para exibir na caixa
     * @param largura Largura total da caixa
     * @see #desenharCaixa(String, String[], int, String)
     */
    public static void desenharCaixa(String titulo, String[] conteudo, int largura) {
        desenharCaixa(titulo, conteudo, largura, ANSIColors.WHITE);
    }

    /**
     * Desenha uma caixa decorativa com bordas, conteúdo e cor customizável.
     * 
     * Exemplo de caixa:
     * ┌────────────────────┐
     * │   Título da Caixa  │
     * ├────────────────────┤
     * │  Linha de conteúdo │
     * │  Outra linha       │
     * └────────────────────┘
     * 
     * @param titulo Título da caixa (pode ser null)
     * @param conteudo Array de strings para exibir
     * @param largura Largura total da caixa
     * @param cor Código ANSI de cor para as bordas
     */
    public static void desenharCaixa(String titulo, String[] conteudo, int largura, String cor) {
        String borda = repeat('─', largura);
        String estilo = cor == null ? "" : cor + ANSIColors.BOLD;
        String reset = cor == null ? "" : ANSIColors.RESET;

        System.out.println(estilo + "┌" + borda + "┐" + reset);
        if (titulo != null && !titulo.isEmpty()) {
            System.out.println(estilo + "│" + centerText(titulo, largura) + "│" + reset);
            System.out.println(estilo + "├" + borda + "┤" + reset);
        }
        for (String linha : conteudo) {
            System.out.println(estilo + "│" + centerText(linha, largura) + "│" + reset);
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
        Animacoes.typeWriter(ANSIColors.WHITE + "Prepare-se para a jornada..." + ANSIColors.RESET, 40);
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


    /**
     * Exibe um menu com opções padrão.
     * Alias para mostrarMenu(titulo, opcoes) com título padrão.
     * 
     * @param opcoes Array de strings representando as opções
     */
    public static void mostrarMenu(String[] opcoes) {
        mostrarMenu("Selecione uma ação:", opcoes);
    }

    /**
     * Exibe um menu customizável com título.
     * Cada opção é numerada de 1 em diante, colorida em amarelo.
     * 
     * Exemplo de saída:
     * Escolha sua classe:
     * 1 - Guerreiro
     * 2 - Mago
     * 3 - Arqueiro
     * 
     * @param titulo O título do menu a exibir
     * @param opcoes Array de strings com as opções disponíveis
     */
    public static void mostrarMenu(String titulo, String[] opcoes) {
        System.out.println(ANSIColors.WHITE + titulo + ANSIColors.RESET);
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println(ANSIColors.YELLOW + (i + 1) + " - " + opcoes[i] + ANSIColors.RESET);
        }
    }

    /**
     * Lê a opção do usuário com validação.
     * Repete solicitação até opção válida ser digitada.
     * Valida que número está entre 1 e quantidade.
     * Retorna índice 0-baseado (opção 1 = índice 0).
     * 
     * Tratamento de exceção:
     * - InputMismatchException: usuário digita não-número
     * - Limpa buffer de entrada
     * - Pede entrada novamente
     * 
     * @param quantidade Número de opções válidas
     * @return Índice da opção escolhida (0-baseado)
     */
    public static int lerOpcao(int quantidade) {
        while (true) {
            System.out.print(ANSIColors.WHITE + "Digite o número da opção e pressione ENTER: " + ANSIColors.RESET);
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();
                if (escolha >= 1 && escolha <= quantidade) {
                    return escolha - 1;
                }
                // Se fora do range, pede novamente
                System.out.println(ANSIColors.RED + "Opção inválida. Digite entre 1 e " + quantidade + "." + ANSIColors.RESET);
            } catch (InputMismatchException e) {
                // Usuário digitou não-número
                scanner.nextLine();  // Limpa buffer
                System.out.println(ANSIColors.RED + "Entrada inválida. Digite um número válido." + ANSIColors.RESET);
            }
        }
    }

    /**
     * Renderiza a tela completa de combate com HUD e sprites.
     * 
     * Layout da tela:
     * - Título da fase no topo
     * - HUD do inimigo (nome, nível, vida)
     * - Sprite ASCII do inimigo
     * - Sprite ASCII do jogador
     * - HUD do jogador (nome, XP, ouro, vida, energia)
     * - Diálogo/mensagem de ação
     * 
     * @param jogador Personagem jogador
     * @param inimigo Inimigo em combate
     * @param inimigoSprite Array de strings com sprite do inimigo
     * @param jogadorSprite Array de strings com sprite do jogador
     * @param titulo Título da fase (ex: "Fase 1")
     * @param dialogo Mensagem de ação ou diálogo
     */
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

    /**
     * Exibe um efeito de texto com cor e negrito.
     * Usado para mensagens de ação e eventos importantes.
     * 
     * @param efeito Mensagem a exibir
     * @param cor Código ANSI da cor desejada
     */
    public static void printEffect(String efeito, String cor) {
        System.out.println(cor + ANSIColors.BOLD + efeito + ANSIColors.RESET);
    }

    /**
     * Pausa a execução e aguarda o usuário pressionar ENTER.
     * Utilizado para pautaçõesou transições entre telas.
     */
    public static void pressEnterToContinue() {
        System.out.print(ANSIColors.WHITE + "Pressione ENTER para continuar..." + ANSIColors.RESET);
        try {
            scanner.nextLine();
        } catch (Exception ignored) {
        }
    }

    /**
     * Lê um texto digitado pelo usuário com prompt customizável.
     * Se entrada ficar vazia, retorna "Herói" como padrão.
     * Limpa o buffer de entrada antes de ler para evitar conflitos.
     * 
     * Tratamento de exceptão:
     * - Exceções gerais retornam "Herói"
     * - Remove espaços em branco das extremidades
     * 
     * @param prompt Mensagem a exibir antes de ler (ex: "Digite seu nome: ")
     * @return Texto digitado pelo usuário ou "Herói" se vazio/erro
     */
    public static String lerTexto(String prompt) {
        flushInputBuffer();
        System.out.print(ANSIColors.WHITE + prompt + ANSIColors.RESET);
        try {
            String valor = scanner.nextLine().trim();
            return valor.isEmpty() ? "Herói" : valor;
        } catch (NoSuchElementException e) {
            // Scanner esgotado (EOF)
            return "Herói";
        } catch (IllegalStateException e) {
            // Scanner foi fechado
            return "Herói";
        } catch (Exception e) {
            // Qualquer outra exceção
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
