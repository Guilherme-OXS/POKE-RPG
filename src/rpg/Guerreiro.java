package rpg;

/**
 * Classe que representa a classe Guerreiro no jogo.
 * 
 * Características (Dificuldade Aumentada):
 * - Vida Máxima: 133 (era 156 - reduzido 15%)
 * - Ataque: 26 (mantido)
 * - Defesa: 14 (era 16 - reduzido 10%)
 * - Energia Máxima: 56 (era 66 - reduzido 15%)
 * 
 * Estratégia: Guerreiro é resiliente e focado em defesa com ataque balanceado.
 * Implementa HabilidadeEspecial (Impacto de Aço) e Recuperavel (recupera vida/energia).
 * 
 * @author Projeto Integrador RPG
 * @see PersonagemBase
 * @see HabilidadeEspecial
 * @see Recuperavel
 */
public class Guerreiro extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    // Constantes de balanceamento da classe
    private static final int TAXA_CRITICO = 20;      // % de chance crítico
    private static final int NUMERO_VARIACAO = 6;    // Variação de dano base
    private static final int MULTIPLIER_CRITICO = 2; // Multiplicador crítico
    private static final int DEFESA_DIVISOR = 2;     // Defesa reduz menos (Guerreiro forte)
    private static final int CUSTO_HABILIDADE = 20;  // Custo de energia
    private static final float MULTIPLIER_HABILIDADE = 1.8f; // 80% mais dano na habilidade
    private static final int RECUPERA_VIDA = 18;     // Vida recuperada
    private static final int RECUPERA_ENERGIA = 20;  // Energia recuperada

    /**
     * Construtor que inicializa um Guerreiro com seus atributos específicos.
     * Stats ajustados para dificuldade: HP -15%, DEF -10%, EN -15%
     * 
     * @param nome Nome do guerreiro
     */
    public Guerreiro(String nome) {
        super(nome, 133, 26, 14, 56);
    }

    /**
     * Guerreiro executa um ataque básico com variação de dano e chance de crítico.
     * 
     * Cálculo: dano = (ataque + variação 0-5) - (defesa_alvo / 2)
     * Crítico (20%): dano duplicado
     * Esquiva (15%): ambos têm chance de esquiva
     * 
     * @param alvo Personagem que sofrerá o ataque
     */
    @Override
    public void atacar(PersonagemBase alvo) {
        // Dano base com variação aleatória
        int base = getAtaque() + (int) (Math.random() * NUMERO_VARIACAO);
        
        // Verifica chance de crítico (20%)
        boolean critico = Math.random() < (TAXA_CRITICO / 100.0);
        if (critico) {
            base *= MULTIPLIER_CRITICO;
            Animacoes.importantEffect("💥 CRÍTICO!");
            Sons.beepCritical();
        } else {
            Sons.beepAttack();
        }
        
        // Calcula dano final: ataque - defesa/2 (guerreiro ignora mais defesa)
        int dano = Math.max(1, base - alvo.getDefesa() / DEFESA_DIVISOR);
        int danoRecebido = alvo.receberDano(dano);
        
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            Animacoes.shakeText("💢 DANO!");
            ConsoleUI.printEffect("Guerreiro causa " + danoRecebido + " de dano.", ANSIColors.RED);
        }
    }

    /**
     * Guerreiro usa sua habilidade especial: Impacto de Aço
     * Requer 20 de energia e causa 1.8x de ataque em dano.
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
        Animacoes.actionAnimation("✨ HABILIDADE: Impacto de Aço");
        
        // Calcula dano ampliado
        int dano = Math.max(1, (int) ((getAtaque() * MULTIPLIER_HABILIDADE) - alvo.getDefesa() / DEFESA_DIVISOR));
        int danoRecebido = alvo.receberDano(dano);
        
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            ConsoleUI.printEffect("✨ Impacto de Aço causa " + danoRecebido + " de dano.", ANSIColors.YELLOW);
            
            // Guerreiro causa SANGRANDO (bleeding) no inimigo (2 turnos)
            // 12% de dano por turno
            alvo.aplicarStatus(Status.SANGRANDO, 2);
            ConsoleUI.printEffect("💧 " + alvo.getNome() + " está sangrando!", ANSIColors.RED);
            
            Sons.beepCritical();
        }
    }

    /**
     * Guerreiro recupera vida e energia.
     * Recupera 18 de vida e 20 de energia.
     */
    @Override
    public void recuperar() {
        Animacoes.actionAnimation("🍃 Recuperando...");
        setVida(getVida() + RECUPERA_VIDA);
        setEnergia(getEnergia() + RECUPERA_ENERGIA);
        ConsoleUI.printEffect("Guerreiro recupera vida e energia.", ANSIColors.GREEN);
    }
}
