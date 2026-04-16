package rpg;

/**
 * Factory class para criar inimigos com diferentes características.
 * 
 * Oferece métodos estáticos para criar inimigos pré-configurados com
 * stats, sprites e comportamentos específicos.
 * 
 * Tipos de inimigos disponíveis:
 * - Goblins (variações por nível)
 * - Esqueletos (variações por nível)
 * - Dragões (variações por nível)
 * - Guerreiros Sombrios
 * - Boss Final
 * - Variantes: Slime, Goku, Mago Sombrio, Arqueiro Sombrio, etc.
 * 
 * @author Projeto Integrador RPG
 * @see Inimigo
 */
public class FabricaInimigos {
    
    // Constantes de dificuldade para cada tipo (Aumentado: HP +25%, ATK +15%, DEF +10%)
    private static final int GOBLIN_FRACO_HP = 50;      // 40 * 1.25
    private static final int GOBLIN_MEDIO_HP = 75;      // 60 * 1.25
    private static final int GOBLIN_FORTE_HP = 106;     // 85 * 1.25
    
    private static final int ESQUELETO_FRACO_HP = 63;   // 50 * 1.25
    private static final int ESQUELETO_MEDIO_HP = 94;   // 75 * 1.25
    private static final int ESQUELETO_FORTE_HP = 138;  // 110 * 1.25
    
    private static final int DRAGAO_FRACO_HP = 125;     // 100 * 1.25
    private static final int DRAGAO_MEDIO_HP = 175;     // 140 * 1.25
    private static final int DRAGAO_FORTE_HP = 238;     // 190 * 1.25
    
    // ==================== GOBLINS ====================
    
    /**
     * Cria um Goblin fraco (início do jogo).
     * Stats: 40 HP, 8 ATK, 3 DEF, 30 EN | XP:50, Gold:25
     * @return Inimigo Goblin configurado
     */
    public static Inimigo criarGoblinFraco() {
        Inimigo goblin = new Inimigo("Goblin Fraco", GOBLIN_FRACO_HP, 8, 3, 30, 
                                     "Golpe Rapido", SpritesASCII.getGoblin(), 50, 25, 1);
        return goblin;
    }
    
    /**
     * Cria um Goblin médio (meio do jogo).
     * Stats: 60 HP, 12 ATK, 4 DEF, 40 EN | XP:70, Gold:35
     * @return Inimigo Goblin configurado
     */
    public static Inimigo criarGoblinMedio() {
        Inimigo goblin = new Inimigo("Goblin Médio", GOBLIN_MEDIO_HP, 12, 4, 40, 
                                     "Golpe Rapido", SpritesASCII.getGoblin(), 70, 35, 2);
        return goblin;
    }
    
    /**
     * Cria um Goblin forte (desafio).
     * Stats: 85 HP, 16 ATK, 6 DEF, 50 EN | XP:100, Gold:50
     * @return Inimigo Goblin configurado
     */
    public static Inimigo criarGoblinForte() {
        Inimigo goblin = new Inimigo("Goblin Forte", GOBLIN_FORTE_HP, 16, 6, 50, 
                                     "Golpe Rapido", SpritesASCII.getGoblin(), 100, 50, 3);
        return goblin;
    }
    
    // ==================== ESQUELETOS ====================
    
    /**
     * Cria um Esqueleto fraco (nível 2).
     * Stats: 50 HP, 10 ATK, 5 DEF, 35 EN | XP:60, Gold:30
     * @return Inimigo Esqueleto configurado
     */
    public static Inimigo criarEsqueletoFraco() {
        Inimigo esqueleto = new Inimigo("Esqueleto Fraco", ESQUELETO_FRACO_HP, 10, 5, 35, 
                                        "Fatia Ossuda", SpritesASCII.getEsqueleto(), 60, 30, 2);
        return esqueleto;
    }
    
    /**
     * Cria um Esqueleto médio (nível 2).
     * Stats: 75 HP, 14 ATK, 7 DEF, 45 EN | XP:80, Gold:42
     * @return Inimigo Esqueleto configurado
     */
    public static Inimigo criarEsqueletoMedio() {
        Inimigo esqueleto = new Inimigo("Esqueleto Médio", ESQUELETO_MEDIO_HP, 14, 7, 45, 
                                        "Fatia Ossuda", SpritesASCII.getEsqueleto(), 80, 42, 2);
        return esqueleto;
    }
    
    /**
     * Cria um Esqueleto forte (nível 2).
     * Stats: 110 HP, 18 ATK, 9 DEF, 55 EN | XP:120, Gold:55
     * @return Inimigo Esqueleto configurado
     */
    public static Inimigo criarEsqueletoForte() {
        Inimigo esqueleto = new Inimigo("Esqueleto Forte", ESQUELETO_FORTE_HP, 18, 9, 55, 
                                        "Fatia Ossuda", SpritesASCII.getEsqueleto(), 120, 55, 3);
        return esqueleto;
    }
    
    // ==================== DRAGÕES ====================
    
    /**
     * Cria um Dragão fraco (nível 3).
     * Stats: 100 HP, 20 ATK, 10 DEF, 60 EN | XP:90, Gold:45
     * @return Inimigo Dragão configurado
     */
    public static Inimigo criarDragaoFraco() {
        Inimigo dragao = new Inimigo("Dragão Fraco", DRAGAO_FRACO_HP, 20, 10, 60, 
                                     "Bafo Flamejante", SpritesASCII.getDragao(), 90, 45, 3);
        return dragao;
    }
    
    /**
     * Cria um Dragão médio (nível 3).
     * Stats: 140 HP, 26 ATK, 12 DEF, 75 EN | XP:120, Gold:60
     * @return Inimigo Dragão configurado
     */
    public static Inimigo criarDragaoMedio() {
        Inimigo dragao = new Inimigo("Dragão Médio", DRAGAO_MEDIO_HP, 26, 12, 75, 
                                     "Bafo Flamejante", SpritesASCII.getDragao(), 120, 60, 4);
        return dragao;
    }
    
    /**
     * Cria um Dragão forte (nível 3).
     * Stats: 190 HP, 32 ATK, 15 DEF, 90 EN | XP:150, Gold:80
     * @return Inimigo Dragão configurado
     */
    public static Inimigo criarDragaoForte() {
        Inimigo dragao = new Inimigo("Dragão Forte", DRAGAO_FORTE_HP, 32, 15, 90, 
                                     "Bafo Flamejante", SpritesASCII.getDragao(), 150, 80, 5);
        return dragao;
    }
    
    // ==================== INIMIGOS ESPECIAIS ====================
    
    /**
     * Cria um Slime muito fraco (tutorial).
     * Stats: 20 HP, 4 ATK, 1 DEF, 15 EN | XP:30, Gold:10
     * @return Inimigo Slime configurado
     */
    public static Inimigo criarSlime() {
        Inimigo slime = new Inimigo("Slime Gelatinoso", 20, 4, 1, 15, 
                                    "Lodo Pegajoso", SpritesASCII.getSlime(), 30, 10, 1);
        return slime;
    }
    
    /**
     * Cria um Goku (guerreiro lutador).
     * Stats: 150 HP, 35 ATK, 14 DEF, 85 EN | XP:110, Gold:70
     * Desafio: Alta velocidade de ataque
     * @return Inimigo Goku configurado
     */
    public static Inimigo criarGoku() {
        Inimigo goku = new Inimigo("Goku Lutador", 150, 35, 14, 85, 
                                   "Kamehameha", SpritesASCII.getGoku(), 110, 70, 4);
        return goku;
    }
    
    /**
     * Cria um Mago Sombrio (mágico especializado).
     * Stats: 90 HP, 38 ATK, 8 DEF, 120 EN | XP:100, Gold:65
     * Desafio: Alto ataque mágico e muita energia
     * @return Inimigo Mago Sombrio configurado
     */
    public static Inimigo criarMagoSombrio() {
        Inimigo mago = new Inimigo("Mago Sombrio", 90, 38, 8, 120, 
                                   "Magia Negra", SpritesASCII.getMagoSombrio(), 100, 65, 3);
        return mago;
    }
    
    /**
     * Cria um Arqueiro Sombrio (críticos letais).
     * Stats: 95 HP, 33 ATK, 10 DEF, 95 EN | XP:105, Gold:68
     * Desafio: Taxa crítica muito alta
     * @return Inimigo Arqueiro Sombrio configurado
     */
    public static Inimigo criarArqueirSombrio() {
        Inimigo arqueiro = new Inimigo("Arqueiro Sombrio", 95, 33, 10, 95, 
                                       "Flecha Letal", SpritesASCII.getArqueiroSombrio(), 105, 68, 3);
        return arqueiro;
    }
    
    /**
     * Cria um Guerreiro Sombrio (imenso).
     * Stats: 180 HP, 28 ATK, 20 DEF, 100 EN | XP:130, Gold:75
     * Desafio: Defesa extremamente alta
     * @return Inimigo Guerreiro Sombrio configurado
     */
    public static Inimigo criarGuerreiroSombrio() {
        Inimigo guerreiro = new Inimigo("Guerreiro Sombrio", 180, 28, 20, 100, 
                                        "Golpe Devastador", SpritesASCII.getGuerreiroSombrio(), 130, 75, 5);
        return guerreiro;
    }
    
    /**
     * Cria um Cavaleiro (versão final antes do boss).
     * Stats: 160 HP, 32 ATK, 18 DEF, 110 EN | XP:125, Gold:70
     * Desafio: Balanceado e muito resistente
     * @return Inimigo Cavaleiro configurado
     */
    public static Inimigo criarCavaleiro() {
        Inimigo cavaleiro = new Inimigo("Cavaleiro Antigo", 160, 32, 18, 110, 
                                        "Ataque Nobre", SpritesASCII.getCavaleiro(), 125, 70, 4);
        return cavaleiro;
    }
    
    /**
     * Cria o Monstro final (boss verdadeiro).
     * Stats: 250 HP, 40 ATK, 22 DEF, 150 EN | XP:200, Gold:120
     * Desafio: Inimigo extremamente poderoso
     * @return Inimigo Monstro configurado
     */
    public static Inimigo criarMonstroFinal() {
        Inimigo monstro = new Inimigo("Monstro Abissal", 250, 40, 22, 150, 
                                      "Rugido Cataclismico", SpritesASCII.getBoss(), 200, 120, 6);
        return monstro;
    }
    
    /**
     * Cria um inimigo aleatório para eventos especiais.
     * Escolhe entre todos os tipos disponíveis.
     * 
     * @return Inimigo aleatório
     */
    public static Inimigo criarInimigoAleatorio() {
        int random = (int)(Math.random() * 11);
        
        switch (random) {
            case 0: return criarGoblinFraco();
            case 1: return criarGoblinMedio();
            case 2: return criarEsqueletoFraco();
            case 3: return criarEsqueletoMedio();
            case 4: return criarDragaoFraco();
            case 5: return criarSlime();
            case 6: return criarGoku();
            case 7: return criarMagoSombrio();
            case 8: return criarArqueirSombrio();
            case 9: return criarCavaleiro();
            case 10: return criarMonstroFinal();
            default: return criarGoblinFraco();
        }
    }
}
