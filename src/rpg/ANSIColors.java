package rpg;

/**
 * Classe utilitária que armazena códigos ANSI para coloração de texto no console.
 * Permite a exibição de mensagens coloridas e estilizadas no terminal.
 * 
 * Exemplo de uso:
 * <pre>
 *   System.out.println(ANSIColors.GREEN + "Saúde! " + ANSIColors.RESET);
 * </pre>
 * 
 * @author Projeto Integrador RPG
 * @see ConsoleUI
 */
public class ANSIColors {
    // Cores padrão
    /** Código ANSI para resetar a cor/estilo ao padrão */
    public static final String RESET = "\u001B[0m";
    
    /** Código ANSI para a cor vermelha */
    public static final String RED = "\u001B[31m";
    
    /** Código ANSI para a cor verde */
    public static final String GREEN = "\u001B[32m";
    
    /** Código ANSI para a cor amarela */
    public static final String YELLOW = "\u001B[33m";
    
    /** Código ANSI para a cor azul */
    public static final String BLUE = "\u001B[34m";
    
    /** Código ANSI para a cor púrpura */
    public static final String PURPLE = "\u001B[35m";
    
    /** Código ANSI para a cor ciano */
    public static final String CYAN = "\u001B[36m";
    
    /** Código ANSI para a cor branca */
    public static final String WHITE = "\u001B[37m";
    
    // Estilos
    /** Código ANSI para texto em negrito */
    public static final String BOLD = "\u001B[1m";
    
    /** Código ANSI para texto sublinhado */
    public static final String UNDERLINE = "\u001B[4m";
    
    // Construtor privado para evitar instanciação
    private ANSIColors() {
        // Classe utilitária pura, não deve ser instanciada
    }
}
