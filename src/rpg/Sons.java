package rpg;

public class Sons {
    public static void beepAttack() {
        playBeep(2);
    }

    public static void beepCritical() {
        playBeep(3);
    }

    public static void beepVictory() {
        playBeep(4);
    }

    public static void beepDefeat() {
        playBeep(1);
    }

    private static void playBeep(int times) {
        for (int i = 0; i < times; i++) {
            System.out.print("\007");
            System.out.flush();
            try {
                Thread.sleep(120);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
