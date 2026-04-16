package rpg;

/**
 * Classe que gerencia os status (efeitos) aplicados a um personagem.
 * 
 * Responsabilidades:
 * - Aplicar e remover status de forma segura
 * - Controlar duração de cada status em turnos
 * - Calcular modificadores de atributos baseado nos status ativos
 * - Aplicar dano passivo de debuffs
 * - Gerenciar status simultâneos (um personagem pode ter até um status por vez)
 * 
 * Exemplo: Personagem com QUEIMADO(3 turnos) recebe 15% de dano por turno
 * e seu ataque é reduzido em 15%.
 * 
 * @author Projeto Integrador RPG
 * @see Status
 * @see PersonagemBase
 */
public class StatusManager {
    // Constantes de dano e modificadores
    private static final float DANO_ENVENENADO = 0.10f;    // 10% de dano por turno
    private static final float DANO_QUEIMADO = 0.15f;      // 15% de dano por turno
    private static final float DANO_CONGELADO = 0.08f;     // 8% de dano por turno
    private static final float DANO_SANGRANDO = 0.12f;     // 12% de dano por turno
    
    // Modificadores de atributos
    private static final float REDUCAO_DEFESA_ENVENENADO = 0.80f;  // -20% defesa
    private static final float REDUCAO_ATAQUE_QUEIMADO = 0.85f;    // -15% ataque
    private static final float REDUCAO_VELOCIDADE_CONGELADO = 0.10f; // 10% não ataca
    private static final float REDUCAO_DANO_PROTEGIDO = 0.75f;     // -25% dano
    private static final float AUMENTO_ATAQUE_FURIA = 1.30f;       // +30% ataque
    private static final float REDUCAO_DEFESA_FURIA = 0.90f;       // -10% defesa
    
    // Atributos do gerenciador
    private Status statusAtual;
    private int turnosRestantes;
    
    /**
     * Construtor que inicializa o gerenciador sem status ativo.
     */
    public StatusManager() {
        this.statusAtual = Status.NENHUM;
        this.turnosRestantes = 0;
    }
    
    /**
     * Aplica um novo status ao personagem.
     * Substitui status anterior se houver um ativo.
     * 
     * @param novoStatus Status a ser aplicado
     * @param duracao Duração em turnos (3-5 típico)
     * @return Mensagem descrevendo o status aplicado
     */
    public String aplicarStatus(Status novoStatus, int duracao) {
        if (novoStatus == Status.NENHUM) {
            return "Nenhum status aplicado.";
        }
        
        this.statusAtual = novoStatus;
        this.turnosRestantes = duracao;
        
        return novoStatus.getEmoji() + " " + novoStatus.getNomeStatus() + 
               " aplicado por " + duracao + " turnos!";
    }
    
    /**
     * Reduz a duração do status em 1 turno.
     * Remove automaticamente se duração chegar a 0.
     * 
     * @return true se o status expirou neste turno
     */
    public boolean reduzirDuracao() {
        if (statusAtual == Status.NENHUM) {
            return false;
        }
        
        turnosRestantes--;
        
        if (turnosRestantes <= 0) {
            statusAtual = Status.NENHUM;
            turnosRestantes = 0;
            return true;
        }
        
        return false;
    }
    
    /**
     * Calcula o dano passivo causado pelo status (debuff) atual.
     * Aplicado a cada turno do personagem afetado.
     * 
     * @param vidaMaxima Vida máxima do personagem (base para %dano)
     * @return Dano a ser aplicado (0 se sem debuff)
     */
    public int calcularDanoPassivo(int vidaMaxima) {
        switch (statusAtual) {
            case ENVENENADO:
                return Math.max(1, (int)(vidaMaxima * DANO_ENVENENADO));
            case QUEIMADO:
                return Math.max(1, (int)(vidaMaxima * DANO_QUEIMADO));
            case CONGELADO:
                return Math.max(1, (int)(vidaMaxima * DANO_CONGELADO));
            case SANGRANDO:
                return Math.max(1, (int)(vidaMaxima * DANO_SANGRANDO));
            default:
                return 0;  // Sem dano passivo
        }
    }
    
    /**
     * Calcula o modificador de ataque baseado no status atual.
     * 
     * @return Multiplicador de ataque (1.0 = sem modificação)
     */
    public float getModificadorAtaque() {
        switch (statusAtual) {
            case QUEIMADO:
                return REDUCAO_ATAQUE_QUEIMADO;  // -15% ataque
            case FURIA:
                return AUMENTO_ATAQUE_FURIA;     // +30% ataque
            default:
                return 1.0f;  // Sem modificação
        }
    }
    
    /**
     * Calcula o modificador de defesa baseado no status atual.
     * 
     * @return Multiplicador de defesa (1.0 = sem modificação)
     */
    public float getModificadorDefesa() {
        switch (statusAtual) {
            case ENVENENADO:
                return REDUCAO_DEFESA_ENVENENADO;  // -20% defesa
            case FURIA:
                return REDUCAO_DEFESA_FURIA;       // -10% defesa
            default:
                return 1.0f;  // Sem modificação
        }
    }
    
    /**
     * Calcula o divisor de dano baseado no status de proteção.
     * Reduz dano recebido se PROTEGIDO.
     * 
     * @return Multiplicador de dano (1.0 = sem redução, 0.75 = -25%)
     */
    public float getModificadorDano() {
        if (statusAtual == Status.PROTEGIDO) {
            return REDUCAO_DANO_PROTEGIDO;  // -25% dano
        }
        return 1.0f;  // Sem redução
    }
    
    /**
     * Retorna a chance de se recusar a atacar (congelado, etc).
     * 
     * @return Probabilidade de falhar ação (0.0 = 0%, 1.0 = 100%)
     */
    public float getProbabilidadeRecusa() {
        if (statusAtual == Status.CONGELADO) {
            return REDUCAO_VELOCIDADE_CONGELADO;  // 10% chance de não atacar
        }
        return 0.0f;  // Sem chance de recusa
    }
    
    /**
     * Retorna o status atual do personagem.
     * 
     * @return Status ativo (NENHUM se sem efeitos)
     */
    public Status getStatusAtual() {
        return statusAtual;
    }
    
    /**
     * Retorna turnos restantes do status atual.
     * 
     * @return Número de turnos (0 se sem status)
     */
    public int getTurnosRestantes() {
        return turnosRestantes;
    }
    
    /**
     * Retorna descrição textual do status para exibição na UI.
     * Exemplo: "🔥 Queimado (2 turnos)"
     * 
     * @return String descrevendo status e duração
     */
    public String getDescricaoStatus() {
        if (statusAtual == Status.NENHUM) {
            return "✨ Normal";
        }
        return statusAtual.getEmoji() + " " + statusAtual.getNomeStatus() + 
               " (" + turnosRestantes + " turnos)";
    }
    
    /**
     * Remove qualquer status ativo (reset para NENHUM).
     * Util para restauração total (poção, habilidade).
     */
    public void limparStatus() {
        this.statusAtual = Status.NENHUM;
        this.turnosRestantes = 0;
    }
}
