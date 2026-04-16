package rpg;

/**
 * Classe que representa a classe Arqueiro no jogo.
 * 
 * Características (Dificuldade Aumentada):
 * - Vida Máxima: 98 (era 115 - reduzido 15%)
 * - Ataque: 28 (mantido - médio-alto)
 * - Defesa: 10 (era 11 - reduzido 10%)
 * - Energia Máxima: 68 (era 80 - reduzido 15%)
 * 
 * Estratégia: Arqueiro é balanceado com alta chance de crítico.
 * Habilidade especial (Chuva de Flechas) atira múltiplas vezes com fração de dano.
 * Implementa HabilidadeEspecial (Chuva de Flechas) e Recuperavel (recupera vida/energia).
 * 
 * @author Projeto Integrador RPG
 * @see PersonagemBase
 * @see HabilidadeEspecial
 * @see Recuperavel
 */
public class Arqueiro extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    // Constantes de balanceamento da classe
    private static final int TAXA_CRITICO = 30;      // % de chance crítico (melhor do jogo)
    private static final int NUMERO_VARIACAO = 10;   // Variação de dano base (maior chance de variação)
    private static final int MULTIPLIER_CRITICO = 2; // Multiplicador crítico
    private static final int DEFESA_DIVISOR = 3;     // Defesa reduz (arrows penetram)
    private static final int CUSTO_HABILIDADE = 20;  // Custo de energia
    private static final int NUM_FLECHAS = 3;        // Número de flechas na habilidade
    private static final float MULTIPLIER_FLECHA = 0.9f; // 90% de dano por flecha
    private static final int DIVISOR_FLECHA = 5;     // Defesa reduz menos cada flecha
    private static final int RECUPERA_VIDA = 16;     // Vida recuperada
    private static final int RECUPERA_ENERGIA = 22;  // Energia recuperada

    /**
     * Construtor que inicializa um Arqueiro com seus atributos específicos.
     * Stats ajustados para dificuldade: HP -15%, DEF -10%, EN -15%
     * 
     * @param nome Nome do arqueiro
     */
    public Arqueiro(String nome) {
        super(nome, 98, 28, 10, 68);
    }

    /**
     * Arqueiro executa um ataque com arco com alta chance de crítico.
     * 
     * Cálculo: dano = (ataque + variação 0-9) - (defesa_alvo / 3)
     * Crítico (30%): dano duplicado - MELHOR TAXA DE CRÍTICO
     * Esquiva (15%): ambos têm chance de esquiva
     * 
     * @param alvo Personagem que sofrerá o ataque
     */
    @Override
    public void atacar(PersonagemBase alvo) {
        // Dano base com variação aleatória (maior variabilidade)
        int base = getAtaque() + (int) (Math.random() * NUMERO_VARIACAO);
        
        // Verifica chance de crítico (30% - melhor taxa de crítico)
        boolean critico = Math.random() < (TAXA_CRITICO / 100.0);
        if (critico) {
            base *= MULTIPLIER_CRITICO;
            Animacoes.importantEffect("💥 Tiro crítico!");
            Sons.beepCritical();
        } else {
            Sons.beepAttack();
        }
        
        // Calcula dano final: ataque - defesa/3 (flechas penetram defesa)
        int dano = Math.max(1, base - alvo.getDefesa() / DEFESA_DIVISOR);
        int danoRecebido = alvo.receberDano(dano);
        
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            Animacoes.shakeText("💢 DANO!");
            ConsoleUI.printEffect("Arqueiro acerta flecha e causa " + danoRecebido + " de dano.", ANSIColors.RED);
        }
    }

    /**
     * Arqueiro usa sua habilidade especial: Chuva de Flechas
     * Requer 20 de energia e atira 3 flechas, cada uma com 0.9x de ataque.
     * Causa dano múltiplo em vez de dano único amplificado.
     * 
     * @param alvo Alvo da habilidade especial
     */
    @Override
    public void usarHabilidade(PersonagemBase alvo) {
        // Valida se tem energia suficiente
        if (getEnergia() < CUSTO_HABILIDADE) {
            ConsoleUI.printEffect("Energia insuficiente para habilidade!", ANSIColors.RED);
            return;
        }
        
        // Consome energia da habilidade
        setEnergia(getEnergia() - CUSTO_HABILIDADE);
        Animacoes.actionAnimation("✨ HABILIDADE: Chuva de Flechas");
        
        // Atira 3 flechas, cada uma causando dano reduzido
        int totalDano = 0;
        for (int i = 0; i < NUM_FLECHAS; i++) {
            int dano = Math.max(1, (int) ((getAtaque() * MULTIPLIER_FLECHA) - alvo.getDefesa() / DIVISOR_FLECHA));
            totalDano += alvo.receberDano(dano);
        }
        
        if (totalDano == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            ConsoleUI.printEffect("✨ Chuva de Flechas causa " + totalDano + " de dano.", ANSIColors.YELLOW);
            
            // Arqueiro causa CONGELADO (freeze) com gelo nas flechas (2 turnos)
            // 8% de dano por turno e 10% chance de pular turno
            alvo.aplicarStatus(Status.CONGELADO, 2);
            ConsoleUI.printEffect("❄️ " + alvo.getNome() + " foi congelado!", ANSIColors.CYAN);
            
            Sons.beepCritical();
        }
    }

    /**
     * Arqueiro recupera vida e energia de forma balanceada.
     * Recupera 16 de vida e 22 de energia.
     */
    @Override
    public void recuperar() {
        Animacoes.actionAnimation("🍃 Recuperando...");
        setVida(getVida() + RECUPERA_VIDA);
        setEnergia(getEnergia() + RECUPERA_ENERGIA);
        ConsoleUI.printEffect("Arqueiro recupera vida e energia.", ANSIColors.GREEN);
    }
}
