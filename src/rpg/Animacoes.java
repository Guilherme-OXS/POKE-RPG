package rpg;

public class Animacoes {
    public static final int DELAY_CURTO = 200;
    public static final int DELAY_MEDIO = 500;
    public static final int DELAY_LONGO = 1000;

    public static void typeWriter(String text, int delayMillis) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            sleep(delayMillis);
        }
        System.out.println();
    }

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

    public static void shakeText(String text) {
        try {
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

    public static void actionAnimation(String action) {
        System.out.print(ANSIColors.PURPLE + action + ANSIColors.RESET);
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            sleep(DELAY_MEDIO);
        }
        System.out.println();
    }

    public static void importantEffect(String effect) {
        System.out.println(ANSIColors.YELLOW + ANSIColors.BOLD + effect + ANSIColors.RESET);
        sleep(DELAY_LONGO);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
