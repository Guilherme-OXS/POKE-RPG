package rpg;

/**
 * Classe que representa um inimigo/boss controlado pela IA do jogo.
 * 
 * Cada inimigo é único com seus próprios atributos, sprite e habilidade.
 * Herda de PersonagemBase e implementa interfaces para habilidades e recuperação.
 * 
 * A IA inimiga segue uma lógica adaptativa em decidirAcao():
 * - Quando com baixa vida: prioriza defesa e recuperação
 * - Quando com energia: usa habilidade especial
 * - Quando em situação normal: escolhe entre ataque e defesa
 * 
 * Inimigos derrotados concedem XP e ouro ao jogador.
 * 
 * @author Projeto Integrador RPG
 * @see PersonagemBase
 * @see HabilidadeEspecial
 * @see Recuperavel
 */
public class Inimigo extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    // Constantes de IA
    private static final int CUSTO_HABILIDADE = 18;      // Custo de energia para habilidade
    private static final float MULTIPLIER_HABILIDADE = 1.9f; // 90% a mais de dano
    private static final int DIVISOR_DEFESA = 3;         // Defesa reduz menos na habilidade
    private static final int RECUPERA_VIDA = 14;         // Vida recuperada
    private static final int RECUPERA_ENERGIA = 18;      // Energia recuperada
    private static final int TAXA_CRITICO = 18;          // % de chance crítico (menor que jogador)
    private static final int NUMERO_VARIACAO = 8;        // Variação de dano base
    private static final int TAXA_CRITICO_MULTI = 2;     // Multiplicador crítico
    private static final int DIVISOR_DEFESA_NORMAL = 2;  // Divisor defesa ataque normal
    
    // Limiares de IA para decisão
    private static final double VITAL_BAIXA = 0.3;       // 30% de vida = vital baixa
    private static final double PROBABILIDADE_DEFENDER_CRITICO = 0.55;  // Chance de defender se vital
    private static final double PROBABILIDADE_USAR_HABILIDADE = 0.4;    // Chance de usar habilidade
    private static final double VIDA_MEDIA = 0.5;        // 50% de vida = vida média
    private static final double ENERGIA_MINIMA = 10.0;   // Energia mínima para recuperar
    private static final double PROBABILIDADE_RECUPERAR = 0.65; // Chance de recuperar
    private static final double PROBABILIDADE_DEFESA_NORMAL = 0.25; // Chance de defender normalmente
    
    // Atributos específicos do inimigo
    private final String habilidadeNome;
    private final String[] sprite;
    private final int xpDrop;
    private final int ouroDrop;

    /**
     * Construtor que cria um inimigo com atributos customizados.
     * 
     * @param nome Nome do inimigo
     * @param vidaMaxima Vida máxima
     * @param ataque Valor de ataque
     * @param defesa Valor de defesa
     * @param energiaMaxima Energia máxima
     * @param habilidadeNome Nome da habilidade especial
     * @param sprite Array de strings representa ndo o sprite ASCII
     * @param xpDrop Experiência que o inimigo concede
     * @param ouroDrop Ouro que o inimigo concede
     * @param nivel Nível do inimigo (afeta dificuldade)
     */
    public Inimigo(String nome, int vidaMaxima, int ataque, int defesa, int energiaMaxima, String habilidadeNome, String[] sprite, int xpDrop, int ouroDrop, int nivel) {
        super(nome, vidaMaxima, ataque, defesa, energiaMaxima);
        this.habilidadeNome = habilidadeNome;
        this.sprite = sprite;
        this.xpDrop = xpDrop;
        this.ouroDrop = ouroDrop;
        setNivel(nivel);
    }

    /**
     * Retorna o sprite ASCII do inimigo para visualizar em combate.
     * 
     * @return Array de strings representando linhas do sprite
     */
    public String[] getSprite() {
        return sprite;
    }

    /**
     * Retorna a experiência que este inimigo concede quando derrotado.
     * 
     * @return Quantidade de XP
     */
    public int getXpDrop() {
        return xpDrop;
    }

    /**
     * Retorna o ouro que este inimigo soltar quando derrotado.
     * 
     * @return Quantidade de ouro
     */
    public int getOuroDrop() {
        return ouroDrop;
    }

    /**
     * Inimigo executa um ataque básico contra o jogador.
     * 
     * Cálculo: dano = (ataque + variação 0-7) - (defesa_alvo / 2)
     * Crítico (18%): dano duplicado
     * 
     * @param alvo Jogador que sofrerá o ataque
     */
    @Override
    public void atacar(PersonagemBase alvo) {
        // Dano base com variação aleatória
        int base = getAtaque() + (int) (Math.random() * NUMERO_VARIACAO);
        
        // Verifica chance de crítico
        boolean critico = Math.random() < (TAXA_CRITICO / 100.0);
        if (critico) {
            base *= TAXA_CRITICO_MULTI;
            Animacoes.importantEffect("💥 CRÍTICO INIMIGO!");
            Sons.beepCritical();
        } else {
            Sons.beepAttack();
        }
        
        // Calcula dano final
        int dano = Math.max(1, base - alvo.getDefesa() / DIVISOR_DEFESA_NORMAL);
        int danoRecebido = alvo.receberDano(dano);
        
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 O inimigo errou o ataque!", ANSIColors.PURPLE);
        } else {
            Animacoes.shakeText("💢 DANO!");
            ConsoleUI.printEffect(getNome() + " causa " + danoRecebido + " de dano.", ANSIColors.RED);
        }
    }

    /**
     * Inimigo usa sua habilidade especial contra o jogador.
     * Se não tiver energia suficiente, ataca normalmente.
     * 
     * @param alvo Jogador que sofrerá a habilidade
     */
    @Override
    public void usarHabilidade(PersonagemBase alvo) {
        // Se não tem energia, ataca normalmente
        if (getEnergia() < CUSTO_HABILIDADE) {
            atacar(alvo);
            return;
        }
        
        // Consome energia da habilidade
        setEnergia(getEnergia() - CUSTO_HABILIDADE);
        Animacoes.actionAnimation("✨ HABILIDADE: " + habilidadeNome);
        
        // Calcula dano ampliado
        int dano = Math.max(1, (int) ((getAtaque() * MULTIPLIER_HABILIDADE) - alvo.getDefesa() / DIVISOR_DEFESA));
        int danoRecebido = alvo.receberDano(dano);
        
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 O inimigo falhou a habilidade!", ANSIColors.PURPLE);
        } else {
            ConsoleUI.printEffect(getNome() + " usa " + habilidadeNome + " e causa " + danoRecebido + " de dano.", 
                                ANSIColors.YELLOW);
            
            // Inimigo aplica ENVENENADO ao usar habilidade (3 turnos)
            // 10% de dano por turno e -20% defesa
            alvo.aplicarStatus(Status.ENVENENADO, 3);
            ConsoleUI.printEffect("🐍 " + alvo.getNome() + " foi envenenado!", ANSIColors.GREEN);
            
            Sons.beepCritical();
        }
    }

    /**
     * Inimigo recupera sua vida e energia.
     * Recupera 14 de vida e 18 de energia.
     */
    @Override
    public void recuperar() {
        Animacoes.actionAnimation("🍄 Inimigo recuperando...");
        setVida(getVida() + RECUPERA_VIDA);
        setEnergia(getEnergia() + RECUPERA_ENERGIA);
        ConsoleUI.printEffect(getNome() + " recupera vida e energia.", ANSIColors.GREEN);
    }

    /**
     * IA do inimigo que decide qual ação tomar no turno atual.
     * 
     * Lógica de decisão (em ordem de prioridade):
     * 1. Se vida < 30%: Tenta defender (55% chance) ou assume postura defensiva (25% chance)
     * 2. Se energia >= 18: Tenta usar habilidade (40% chance)
     * 3. Se vida < 50% e pode recuperar: Tenta recuperar (65% chance)
     * 4. Caso contrário: 75% ataque + 25% defesa
     * 
     * Esta IA adaptativa cria comportamentos believable e desafiadores.
     * 
     * @param jogador O jogador para atacar/interagir
     */
    public void decidirAcao(PersonagemBase jogador) {
        double motivacao = Math.random();  // Valor aleatório 0-1 para adicionar variância
        
        // Prioridade 1: DEFESA CRÍTICA - Vida muito baixa
        if (getVida() < getVidaMaxima() * VITAL_BAIXA && motivacao < PROBABILIDADE_DEFENDER_CRITICO) {
            defender();
            ConsoleUI.printEffect(getNome() + " está se defendendo!", ANSIColors.PURPLE);
            return;
        }
        
        // Prioridade 2: HABILIDADE - Tem energia e ocasião favorável
        if (getEnergia() >= CUSTO_HABILIDADE && motivacao < PROBABILIDADE_USAR_HABILIDADE) {
            usarHabilidade(jogador);
            return;
        }
        
        // Prioridade 3: RECUPERAÇÃO - Vida média-baixa e energia disponível
        if (getVida() < getVidaMaxima() * VIDA_MEDIA && getEnergia() >= ENERGIA_MINIMA && motivacao < PROBABILIDADE_RECUPERAR) {
            recuperar();
            return;
        }
        
        // Prioridade 4: DEFESA ou ATAQUE - Finalmente, escolhe entre defesa ou ataque
        if (motivacao < PROBABILIDADE_DEFESA_NORMAL) {
            defender();
            ConsoleUI.printEffect(getNome() + " assume postura defensiva.", ANSIColors.PURPLE);
        } else {
            atacar(jogador);
        }
    }
}
