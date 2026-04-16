package rpg;

/**
 * Classe utilitária que armazena sprites ASCII de personagens e inimigos.
 * Cada sprite é representado como um array de strings, onde cada string é uma linha.
 * 
 * Todos os sprites têm 10 linhas padrão e são centralizados para exibição.
 * Os sprites são usados para criar a visualização textual de combate.
 * 
 * @author Projeto Integrador RPG
 * @see ConsoleUI#renderTela(PersonagemBase, Inimigo, String[], String[], String, String)
 */
public class SpritesASCII {
    
    // Construtor privado para evitar instanciação
    private SpritesASCII() {
        // Classe utilitária pura, não deve ser instanciada
    }
    
    /**
     * Retorna o sprite do jogador - visão genérica do herói.
     * 
     * @return Array de 10 strings representando o sprite do jogador
     */
    public static String[] getJogador() {
        return new String[]{
                "                       ",
                "                       ",
                "           O           ",
                "          /|\\          ",
                "          / \\          ",
                "                       ",
                "                       ",
                "                       ",
                "                       ",
                "                       "
        };
    }

    /**
     * Retorna o sprite do Guerreiro com armadura e proteção.
     * Mostra um personagem forte com equipamento pesado.
     * 
     * @return Array de 10 strings representando o sprite do Guerreiro
     */
    public static String[] getGuerreiro() {
        return new String[]{
                "         /~~~\\         ",
                "        | O O |        ",
                "        |  ^  |        ",
                "        | /|\\ |        ",
                "        |  |  |        ",
                "        |  |  |        ",
                "       /   |   \\       ",
                "      /    |    \\      ",
                "     /     |     \\     ",
                "    /______|______\\    "
        };
    }

    /**
     * Retorna o sprite do Mago com chapéu pontudo e robes.
     * Mostra um personagem místico com equipamento mágico.
     * 
     * @return Array de 10 strings representando o sprite do Mago
     */
    public static String[] getMago() {
        return new String[]{
                "           ^           ",
                "          / \\         ",
                "         /___\\        ",
                "         | O |        ",
                "         |   |        ",
                "         |   |        ",
                "        /|   |\\       ",
                "       / |   | \\      ",
                "      /  |   |  \\     ",
                "     /___|___|___\\    "
        };
    }

    /**
     * Retorna o sprite do Arqueiro com arco e flechas.
     * Mostra um personagem ágil com equipamento de alcance.
     * 
     * @return Array de 10 strings representando o sprite do Arqueiro
     */
    public static String[] getArqueiro() {
        return new String[]{
                "           O           ",
                "          /|\\         ",
                "         / | \\        ",
                "        /  |  \\       ",
                "       /   |   \\      ",
                "      /    |    \\     ",
                "     /     |     \\    ",
                "    <-     |     ->    ",
                "      /   |   \\      ",
                "      /    |    \\     "
        };
    }

    /**
     * Retorna o sprite do Goblin - inimigo fraco.
     * Primeira fase do jogo.
     * 
     * @return Array de 10 strings representando o sprite do Goblin
     */
    public static String[] getGoblin() {
        return new String[]{
                "          / \\          ",
                "        _(o o)_        ",
                "        \\  V  /        ",
                "         | | |         ",
                "         | | |         ",
                "        /  |  \\        ",
                "       /   |   \\       ",
                "      /    |    \\      ",
                "     /     |     \\     ",
                "    /      |      \\    "
        };
    }

    /**
     * Retorna o sprite do Esqueleto - inimigo moderado.
     * Segunda fase do jogo.
     * 
     * @return Array de 10 strings representando o sprite do Esqueleto
     */
    public static String[] getEsqueleto() {
        return new String[]{
                "          \\O/          ",
                "           |           ",
                "          /|\\         ",
                "          / \\         ",
                "         /   \\        ",
                "        /  |  \\       ",
                "       /   |   \\      ",
                "      /    |    \\     ",
                "     /     |     \\    ",
                "    /      |      \\   "
        };
    }

    /**
     * Retorna o sprite do Dragão - inimigo forte.
     * Terceira fase do jogo.
     * 
     * @return Array de 10 strings representando o sprite do Dragão
     */
    public static String[] getDragao() {
        return new String[]{
                "        /\\   /\\        ",
                "       /  \\_/  \\       ",
                "      /  ^   ^  \\      ",
                "     /   O   O   \\     ",
                "     |     V     |     ",
                "     |  \\_____/  |     ",
                "      \\   | |   /      ",
                "       \\  | |  /       ",
                "        \\ | | /        ",
                "         \\|_|/         "
        };
    }

    /**
     * Retorna o sprite do Guerreiro Sombrio - inimigo muito forte.
     * Quarta fase do jogo.
     * 
     * @return Array de 10 strings representando o sprite do Guerreiro Sombrio
     */
    public static String[] getGuerreiroSombrio() {
        return new String[]{
                "         /\\_/\\         ",
                "        / o o \\        ",
                "       |  ^ ^  |       ",
                "       |  | |  |       ",
                "      /   | |   \\      ",
                "     /    | |    \\     ",
                "    /     | |     \\    ",
                "   /      | |      \\   ",
                "  /       | |       \\  ",
                " /________| |________\\ "
        };
    }

    /**
     * Retorna o sprite do Slime - inimigo adicional.
     * Variante gelatinosa.
     * 
     * @return Array de 10 strings representando o sprite do Slime
     */
    public static String[] getSlime() {
        return new String[]{
                "          ____         ",
                "       _( o  )_       ",
                "      (  o o  )      ",
                "      (   ^   )      ",
                "       \\  -  /       ",
                "        \\___/        ",
                "         / \\         ",
                "        /   \\        ",
                "       /     \\       ",
                "      /       \\      "
        };
    }

    /**
     * Retorna o sprite do Goku - inimigo adicional.
     * Guerreiro lendário.
     * 
     * @return Array de 10 strings representando o sprite do Goku
     */
    public static String[] getGoku() {
        return new String[]{
                "           /\\          ",
                "          /  \\         ",
                "         /____\\        ",
                "          |OO|         ",
                "          |  |         ",
                "         /|  |\\        ",
                "        / |  | \\       ",
                "       /  |  |  \\      ",
                "      /   |  |   \\     ",
                "     /____|  |____\\    "
        };
    }

    /**
     * Retorna o sprite do Mago Sombrio - variante do mago.
     * Versão maligna com poder mágico escuro.
     * 
     * @return Array de 10 strings representando o sprite do Mago Sombrio
     */
    public static String[] getMagoSombrio() {
        return new String[]{
                "           ^           ",
                "          / \\         ",
                "         /___\\        ",
                "        /  O  \\       ",
                "        |  |  |       ",
                "        |  |  |       ",
                "       /   |   \\      ",
                "      /    |    \\     ",
                "     /     |     \\    ",
                "    /______|______\\   "
        };
    }

    /**
     * Retorna o sprite do Arqueiro Sombrio - variante do arqueiro.
     * Versão maligna com flechas sobrenaturais.
     * 
     * @return Array de 10 strings representando o sprite do Arqueiro Sombrio
     */
    public static String[] getArqueiroSombrio() {
        return new String[]{
                "           O           ",
                "          /|\\         ",
                "         / | \\        ",
                "        /  |  \\       ",
                "     <-    |    ->    ",
                "       /   |   \\      ",
                "      /    |    \\     ",
                "     /     |     \\    ",
                "    /      |      \\   ",
                "   /_______|_______\\  "
        };
    }

    /**
     * Retorna o sprite do Monstro - inimigo genérico.
     * Criatura de aparência aterradora.
     * 
     * @return Array de 10 strings representando o sprite do Monstro
     */
    public static String[] getMonstro() {
        return new String[]{
                "           /\\          ",
                "           ||          ",
                "          /==\\         ",
                "         / || \\        ",
                "        /  ||  \\       ",
                "       /   ||   \\      ",
                "      /    ||    \\     ",
                "     /     ||     \\    ",
                "    /      ||      \\   ",
                "   /_______||_______\\  "
        };
    }

    /**
     * Retorna o sprite do Cavaleiro - inimigo robusto.
     * Guerreiro de cavalaria com armadura pesada.
     * 
     * @return Array de 10 strings representando o sprite do Cavaleiro
     */
    public static String[] getCavaleiro() {
        return new String[]{
                "         /~~~\\         ",
                "        /  O  \\        ",
                "       /  /|\\  \\       ",
                "      /   | |   \\      ",
                "      |   | |   |      ",
                "      |   | |   |      ",
                "      |   | |   |      ",
                "      |   | |   |      ",
                "      |   | |   |      ",
                "     /____|_|____\\     "
        };
    }

    /**
     * Retorna o sprite do Boss - inimigo final.
     * Soberano das Trevas - chefe supremo do jogo.
     * Quinta e última fase do jogo.
     * 
     * @return Array de 10 strings representando o sprite do Boss
     */
    public static String[] getBoss() {
        return new String[]{
                "         /^^^\\         ",
                "        /  O  \\        ",
                "       /  /|\\  \\       ",
                "      /  / | \\  \\      ",
                "     /  /  |  \\  \\     ",
                "    /__/   |   \\__\\    ",
                "     |    / \\    |     ",
                "    /     | |     \\    ",
                "   /      | |      \\   ",
                "  /_______| |_______\\  "
        };
    }
}
