package rpg;

/**
 * Enum que define os diferentes tipos de status (efeitos) que podem afetar personagens.
 * 
 * Tipos de Status:
 * - ENVENENADO: Recebe 10% de dano por turno. Reduz defesa em 20%.
 * - QUEIMADO: Recebe 15% de dano por turno. Reduz ataque em 15%.
 * - CONGELADO: Recebe 8% de dano por turno. Reduz velocidade de ataque (90% de chance de atacar).
 * - SANGRANDO: Recebe 12% de dano por turno. Sem redução de atributos.
 * - PROTEGIDO: Reduz 25% de todo dano recebido. Efeito positivo!
 * - FURIA: Aumenta ataque em 30%. Reduz defesa em 10%. Efeito positivo!
 * - NENHUM: Sem status ativo (estado padrão).
 * 
 * Cada status tem duração em turnos e efeitos específicos em combate.
 * 
 * @author Projeto Integrador RPG
 * @see StatusManager
 */
public enum Status {
    /**
     * Status ENVENENADO: veneno reduz vida e defesa por múltiplos turnos.
     */
    ENVENENADO("Envenenado", "🐍", ANSIColors.GREEN),
    
    /**
     * Status QUEIMADO: fogo reduz vida e ataque por múltiplos turnos.
     */
    QUEIMADO("Queimado", "🔥", ANSIColors.RED),
    
    /**
     * Status CONGELADO: gelo reduz vida e velocidade por múltiplos turnos.
     */
    CONGELADO("Congelado", "❄️", ANSIColors.CYAN),
    
    /**
     * Status SANGRANDO: sangramento reduz vida continuamente sem outros efeitos.
     */
    SANGRANDO("Sangrando", "💧", ANSIColors.RED),
    
    /**
     * Status PROTEGIDO: reduz dano recebido (buff positivo).
     */
    PROTEGIDO("Protegido", "🛡️", ANSIColors.BLUE),
    
    /**
     * Status FURIA: aumenta ataque mas reduz defesa (buff positivo mas arriscado).
     */
    FURIA("Fúria", "⚡", ANSIColors.YELLOW),
    
    /**
     * Status NENHUM: sem efeitos ativos (estado padrão).
     */
    NENHUM("Normal", "✨", ANSIColors.WHITE);
    
    // Atributos do status
    private String nomeStatus;
    private String emoji;
    private String cor;
    
    /**
     * Construtor privado do enum Status.
     * 
     * @param nomeStatus Nome descritivo do status
     * @param emoji Emoji representando o status
     * @param cor Cor ANSI para exibição
     */
    Status(String nomeStatus, String emoji, String cor) {
        this.nomeStatus = nomeStatus;
        this.emoji = emoji;
        this.cor = cor;
    }
    
    /**
     * Retorna o nome do status.
     * @return Nome do status
     */
    public String getNomeStatus() {
        return nomeStatus;
    }
    
    /**
     * Retorna o emoji do status.
     * @return Emoji representativo
     */
    public String getEmoji() {
        return emoji;
    }
    
    /**
     * Retorna a cor ANSI do status.
     * @return Cor para exibição terminal
     */
    public String getCor() {
        return cor;
    }
    
    /**
     * Verifica se é um status negativo (debuff).
     * @return true se é debuff, false se é buff ou nenhum
     */
    public boolean isDebuff() {
        return this == ENVENENADO || this == QUEIMADO || this == CONGELADO || this == SANGRANDO;
    }
    
    /**
     * Verifica se é um status positivo (buff).
     * @return true se é buff
     */
    public boolean isBuff() {
        return this == PROTEGIDO || this == FURIA;
    }
}
