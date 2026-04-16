package rpg;

/**
 * Classe que representa a classe Mago no jogo.
 * 
 * Características (Dificuldade Aumentada):
 * - Vida Máxima: 85 (era 100 - reduzido 15%)
 * - Ataque: 32 (mantido - melhor ataque mágico)
 * - Defesa: 8 (era 9 - reduzido 10% - vulnerável)
 * - Energia Máxima: 92 (era 108 - reduzido 15%)
 * 
 * Estratégia: Mago é o personagem de dano mágico, com baixa defesa mas alta inteligência.
 * Implementa HabilidadeEspecial (Bola de Fogo) e Recuperavel (recupera vida/energia com foco em energia).
 * 
 * @author Projeto Integrador RPG
 * @see PersonagemBase
 * @see HabilidadeEspecial
 * @see Recuperavel
 */
public class Mago extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    // Constantes de balanceamento da classe
    private static final int TAXA_CRITICO = 25;      // % de chance crítico (maior que guerreiro)
    private static final int NUMERO_VARIACAO = 8;    // Variação de dano base (maior volatilidade)
    private static final int MULTIPLIER_CRITICO = 2; // Multiplicador crítico
    private static final int DEFESA_DIVISOR = 3;     // Defesa reduz mais (magoIgnora defesa com magia)
    private static final int CUSTO_HABILIDADE = 25;  // Custo de energia (maior)
    private static final float MULTIPLIER_HABILIDADE = 2.2f; // 120% mais dano na habilidade
    private static final int RECUPERA_VIDA = 14;     // Vida recuperada (pouca)
    private static final int RECUPERA_ENERGIA = 25;  // Energia recuperada (muita)

    /**
     * Construtor que inicializa um Mago com seus atributos específicos.
     * Stats ajustados para dificuldade: HP -15%, DEF -10%, EN -15%
     * 
     * @param nome Nome do mago
     */
    public Mago(String nome) {
        super(nome, 85, 32, 8, 92);
    }

    /**
     * Mago executa um ataque mágico com alta variação e alta chance de crítico.
     * 
     * Cálculo: dano = (ataque + variação 0-7) - (defesa_alvo / 3)
     * Crítico (25%): dano duplicado
     * Esquiva (15%): ambos têm chance de esquiva
     * 
     * @param alvo Personagem que sofrerá o ataque
     */
    @Override
    public void atacar(PersonagemBase alvo) {
        // Dano base com variação aleatória (maior variabilidade)
        int base = getAtaque() + (int) (Math.random() * NUMERO_VARIACAO);
        
        // Verifica chance de crítico (25% - mais que guerreiro)
        boolean critico = Math.random() < (TAXA_CRITICO / 100.0);
        if (critico) {
            base *= MULTIPLIER_CRITICO;
            Animacoes.importantEffect("💥 CRÍTICO MÁGICO!");
            Sons.beepCritical();
        } else {
            Sons.beepAttack();
        }
        
        // Calcula dano final: ataque - defesa/3 (magia ignora mais defesa)
        int dano = Math.max(1, base - alvo.getDefesa() / DEFESA_DIVISOR);
        int danoRecebido = alvo.receberDano(dano);
        
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            Animacoes.shakeText("💢 DANO!");
            ConsoleUI.printEffect("Mago lança feitiço e causa " + danoRecebido + " de dano.", ANSIColors.RED);
        }
    }

    /**
     * Mago usa sua habilidade especial: Bola de Fogo
     * Requer 25 de energia e causa 2.2x de ataque em dano.
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
        Animacoes.actionAnimation("✨ HABILIDADE: Bola de Fogo");
        
        // Calcula dano ampliado especialmente alto
        int dano = Math.max(1, (int) ((getAtaque() * MULTIPLIER_HABILIDADE) - alvo.getDefesa() / DEFESA_DIVISOR));
        int danoRecebido = alvo.receberDano(dano);
        
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            ConsoleUI.printEffect("✨ Bola de Fogo causa " + danoRecebido + " de dano.", ANSIColors.YELLOW);
            
            // Mago causa QUEIMADO (burning) no inimigo (3 turnos)
            // 15% de dano por turno e -15% ataque
            alvo.aplicarStatus(Status.QUEIMADO, 3);
            ConsoleUI.printEffect("🔥 " + alvo.getNome() + " foi queimado!", ANSIColors.RED);
            
            Sons.beepCritical();
        }
    }

    /**
     * Mago recupera vida e energia, com foco em energia.
     * Recupera 14 de vida e 25 de energia.
     */
    @Override
    public void recuperar() {
        Animacoes.actionAnimation("🍄 Recuperando...");
        setVida(getVida() + RECUPERA_VIDA);
        setEnergia(getEnergia() + RECUPERA_ENERGIA);
        ConsoleUI.printEffect("Mago recupera vida e energia.", ANSIColors.GREEN);
    }
}
