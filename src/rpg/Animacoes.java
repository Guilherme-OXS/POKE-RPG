package rpg;

/**
 * Classe utilitária que fornece diferentes tipos de animações e efeitos visuais
 * para criar uma experiência mais imersiva no jogo.
 * 
 * Oferece efeitos como: tipo máquina de escrever, barra de carregamento,
 * contador regressivo, efeito de tremulação, animação de ação e efeitos importantes.
 * 
 * @author Projeto Integrador RPG
 * @see ConsoleUI
 */
public class Animacoes {
    /** Delay curto para animações rápidas (200ms) */
    public static final int DELAY_CURTO = 200;
    
    /** Delay médio para animações normais (500ms) */
    public static final int DELAY_MEDIO = 500;
    
    /** Delay longo para animações de espera (1000ms) */
    public static final int DELAY_LONGO = 1000;

    /**
     * Exibe um texto caractere por caractere, simulando efeito de digitação.
     * Cria a sensação de máquina de escrever.
     * 
     * @param text Texto a ser exibido com efeito de digitação
     * @param delayMillis Delay em milissegundos entre cada caractere
     */
    public static void typeWriter(String text, int delayMillis) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            sleep(delayMillis);
        }
        System.out.println();
    }

    /**
     * Exibe uma barra de carregamento animada.
     * A barra preenche progressivamente até 100%.
     * 
     * @param label Texto a exibir antes da barra (ex: "Carregando")
     * @param steps Número de etapas da barra (também determina número de blocos)
     * @param delayMillis Delay em milissegundos entre cada etapa
     */
    public static void loadingBar(String label, int steps, int delayMillis) {
        if (steps <= 0) {
            System.out.println(label + " [####################] 100%");
            return;
        }
        System.out.print(label + " ");
        for (int i = 0; i <= steps; i++) {
            int percent = (i * 100) / steps;
            System.out.print("[");
            for (int block = 0; block < steps; block++) {
                System.out.print(block <= i ? "#" : " ");
            }
            System.out.print("] " + percent + "%\r");
            System.out.flush();
            sleep(delayMillis);
        }
        System.out.println();
    }

    /**
     * Exibe um contador regressivo.
     * Útil para criar tensão antes de importantes eventos.
     * 
     * @param segundos Quantidade de segundos para contar regressivamente
     */
    public static void contador(int segundos) {
        try {
            for (int i = segundos; i > 0; i--) {
                System.out.print(ANSIColors.YELLOW + "  > " + i + "..." + ANSIColors.RESET + "\r");
                Thread.sleep(1000);
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
    }

    /**
     * Cria um efeito de tremulação para destacar texto importante.
     * O texto pisca movendo-se ligeiramente para criar efeito de impacto.
     * 
     * @param text Texto a ser exibido com efeito de tremulação
     */
    public static void shakeText(String text) {
        try {
            // Repete o efeito de tremulação 3 vezes para maior visibilidade
            for (int repeat = 0; repeat < 3; repeat++) {
                System.out.print("   " + text + "\r");
                Thread.sleep(DELAY_CURTO / 2);
                System.out.print(" " + text + "\r");
                Thread.sleep(DELAY_CURTO / 2);
                System.out.print(text + "\r");
                Thread.sleep(DELAY_CURTO / 2);
            }
            System.out.println(text);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Exibe a animação de uma ação com pontos piscando.
     * Cria sensação de ação em progresso.
     * 
     * @param action Descrição da ação (ex: "HABILIDADE: Bola de Fogo")
     */
    public static void actionAnimation(String action) {
        System.out.print(ANSIColors.PURPLE + action + ANSIColors.RESET);
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            sleep(DELAY_MEDIO);
        }
        System.out.println();
    }

    /**
     * Exibe um efeito de destaque para eventos importantes.
     * O texto é mostrado em negrito com cor amarela e pausa de espera.
     * 
     * @param effect Texto do efeito especial a ser destacado
     */
    public static void importantEffect(String effect) {
        System.out.println(ANSIColors.YELLOW + ANSIColors.BOLD + effect + ANSIColors.RESET);
        sleep(DELAY_LONGO);
    }

    /**
     * Pausa a execução por um tempo determinado.
     * Utiliza Thread.sleep() com tratamento apropriado de InterruptedException.
     * 
     * @param millis Tempo em milissegundos para fazer a pausa
     */
    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
