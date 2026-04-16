package rpg;

/**
 * Classe utilitária responsável pela reprodução de efeitos sonoros no jogo.
 * Usa beeps do sistema para criar retroalimentação sonora em eventos importantes.
 * 
 * @author Projeto Integrador RPG
 */
public class Sons {
    // Constantes para controlar o delay entre beeps (em milissegundos)
    private static final int DELAY_BEEP = 120;
    
    /**
     * Reproduz som de ataque básico (2 beeps rápidos).
     */
    public static void beepAttack() {
        playBeep(2);
    }

    /**
     * Reproduz som de ataque crítico (3 beeps rápidos - mais intenso).
     */
    public static void beepCritical() {
        playBeep(3);
    }

    /**
     * Reproduz som de vitória (4 beeps rápidos).
     */
    public static void beepVictory() {
        playBeep(4);
    }

    /**
     * Reproduz som de derrota (1 beep longo).
     */
    public static void beepDefeat() {
        playBeep(1);
    }

    /**
     * Reproduz múltiplos beeps para criar efeitos sonoros.
     * Cada beep é separado por um delay para criar ritmo.
     * 
     * @param times Quantidade de beeps a serem reproduzidos
     */
    private static void playBeep(int times) {
        for (int i = 0; i < times; i++) {
            // \007 é o caractere bell do terminal (beep do sistema)
            System.out.print("\007");
            System.out.flush();
            try {
                Thread.sleep(DELAY_BEEP);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
