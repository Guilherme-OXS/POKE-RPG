package rpg;

/**
 * Classe abstrata base para todos os personagens do jogo (jogador e inimigos).
 * Define os atributos comuns: vida, energia, ataque, defesa, experiência e ouro.
 * 
 * Implementa as regras principais de combate:
 * - Dano recebido = ataque - defesa (com chance de esquiva)
 * - Defesa reduz o próximo dano em 50%
 * - Esquiva tem 15% de chance de sucesso
 * - Ganho de XP leva ao aumento de nível com melhora de atributos
 * 
 * O método atacar() é abstrato e DEVE ser implementado por todas as subclasses
 * para garantir comportamento polimórfico específico de cada classe.
 * 
 * @author Projeto Integrador RPG
 * @see Guerreiro
 * @see Mago
 * @see Arqueiro
 * @see Inimigo
 */
public abstract class PersonagemBase {
    // Atributos de combate
    private String nome;
    private int vida;
    private int vidaMaxima;
    private int ataque;
    private int defesa;
    private int energia;
    private int energiaMaxima;
    
    // Atributos de progressão
    private int nivel;
    private int experiencia;
    private int xpParaSubir;
    private int ouro;
    
    // Estado de combate e efeitos
    private boolean defendendo;
    private StatusManager statusManager;  // Gerencia status/efeitos do personagem

    /**
     * Construtor que inicializa um personagem com seus atributos base.
     * Inicializa vida e energia nos valores máximos, nível 1, e 50 ouro inicial.
     * Também inicializa o gerenciador de status sem efeitos ativos.
     * 
     * @param nome Nome do personagem
     * @param vidaMaxima Quantidade máxima de vida
     * @param ataque Valor base de ataque
     * @param defesa Valor base de defesa
     * @param energiaMaxima Quantidade máxima de energia
     */
    public PersonagemBase(String nome, int vidaMaxima, int ataque, int defesa, int energiaMaxima) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
        this.ataque = ataque;
        this.defesa = defesa;
        this.energiaMaxima = energiaMaxima;
        this.energia = energiaMaxima;
        this.nivel = 1;
        this.experiencia = 0;
        this.defendendo = false;
        this.xpParaSubir = 100;
        this.ouro = 50;
        this.statusManager = new StatusManager();  // Inicializa sem status ativo
    }

    // ==================== GETTERS ====================
    
    /**
     * Retorna o nome do personagem.
     * 
     * @return Nome do personagem
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a vida atual do personagem.
     * 
     * @return Quantidade de vida atual (entre 0 e vidaMaxima)
     */
    public int getVida() {
        return vida;
    }

    /**
     * Retorna a vida máxima do personagem.
     * 
     * @return Quantidade máxima de vida
     */
    public int getVidaMaxima() {
        return vidaMaxima;
    }

    /**
     * Retorna o valor de ataque do personagem.
     * Este valor é base utilizado nos cálculos de dano.
     * 
     * @return Valor de ataque
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Retorna o valor de defesa do personagem.
     * Defesa reduz o dano recebido.
     * 
     * @return Valor de defesa
     */
    public int getDefesa() {
        return defesa;
    }

    /**
     * Retorna a energia atual do personagem.
     * 
     * @return Quantidade de energia atual (entre 0 e energiaMaxima)
     */
    public int getEnergia() {
        return energia;
    }

    /**
     * Retorna a energia máxima do personagem.
     * 
     * @return Quantidade máxima de energia
     */
    public int getEnergiaMaxima() {
        return energiaMaxima;
    }

    /**
     * Retorna o nível atual do personagem.
     * 
     * @return Nível do personagem
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Retorna a experiência acumulada do personagem.
     * 
     * @return Quantidade de experiência
     */
    public int getExperiencia() {
        return experiencia;
    }

    /**
     * Retorna se o personagem está em postura defensiva.
     * 
     * @return true se defendendo, false caso contrário
     */
    public boolean isDefendendo() {
        return defendendo;
    }

    // ==================== SETTERS ====================

    /**
     * Define a vida do personagem com validação segura.
     * Garante que a vida nunca ultrapasse vidaMaxima ou seja negativa.
     * Aplicável para habilidades de cura e dano.
     * 
     * @param vida Valor de vida a definir
     */
    public void setVida(int vida) {
        this.vida = Math.max(0, Math.min(vida, vidaMaxima));
    }

    /**
     * Define a vida máxima do personagem.
     * Utilizado no aumento de nível e compra de itens.
     * 
     * @param vidaMaxima Novo valor de vida máxima
     */
    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    /**
     * Define o valor de ataque do personagem.
     * Utilizado na loja durante compras de equipamento.
     * 
     * @param ataque Novo valor de ataque
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * Define o valor de defesa do personagem.
     * Utilizado na loja durante compras de armadura.
     * 
     * @param defesa Novo valor de defesa
     */
    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    /**
     * Define a energia do personagem com validação segura.
     * Garante que a energia nunca ultrapasse energiaMaxima ou seja negativa.
     * 
     * @param energia Valor de energia a definir
     */
    public void setEnergia(int energia) {
        this.energia = Math.max(0, Math.min(energia, energiaMaxima));
    }

    /**
     * Define a energia máxima do personagem.
     * 
     * @param energiaMaxima Novo valor de energia máxima
     */
    public void setEnergiaMaxima(int energiaMaxima) {
        this.energiaMaxima = energiaMaxima;
    }

    /**
     * Define o nível do personagem.
     * Principalmente utilizado para inicializar inimigos com diferentes dificuldades.
     * 
     * @param nivel Novo nível do personagem
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Define a experiência do personagem.
     * 
     * @param experiencia Nova quantidade de experiência
     */
    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    // ==================== MÉTODOS STATUS/EFEITOS ====================

    /**
     * Retorna o gerenciador de status do personagem.
     * Permite acessar e modificar efeitos aplicados.
     * 
     * @return StatusManager contendo efeitos ativos
     */
    public StatusManager getStatusManager() {
        return statusManager;
    }

    /**
     * Aplica um status (efeito) ao personagem por um número de turnos.
     * Pode ser debuff (veneno, queimadura) ou buff (proteção, fúria).
     * 
     * @param status Status a aplicar (ex: Status.QUEIMADO)
     * @param duracao Número de turnos que o status durará
     * @return Mensagem descrevendo o efeito aplicado
     */
    public String aplicarStatus(Status status, int duracao) {
        return statusManager.aplicarStatus(status, duracao);
    }

    /**
     * Processa o efeito passivo do status ao final de cada turno.
     * Aplica dano de debuffs e reduz duração.
     * 
     * @return Dano sofrido pelos efeitos (0 se sem debuff ativo)
     */
    public int procesarStatusTurno() {
        // Calcula dano passivo (se houver debuff)
        int danoPassivo = statusManager.calcularDanoPassivo(vidaMaxima);
        
        if (danoPassivo > 0) {
            // Aplica dano do status
            vida = Math.max(0, vida - danoPassivo);
            ConsoleUI.printEffect(getNome() + " sofre " + danoPassivo + 
                                " de dano por " + statusManager.getStatusAtual().getNomeStatus(), 
                                statusManager.getStatusAtual().getCor());
        }
        
        // Reduz duração do status e verifica se expirou
        if (statusManager.reduzirDuracao()) {
            ConsoleUI.printEffect(getNome() + " não está mais " + 
                                statusManager.getStatusAtual().getNomeStatus(), 
                                ANSIColors.GREEN);
        }
        
        return danoPassivo;
    }

    /**
     * Retorna descrição textual do status atual para exibição na UI.
     * Exemplo: "🔥 Queimado (2 turnos)"
     * 
     * @return String com ícone, nome e duração do status
     */
    public String getDescricaoStatus() {
        return statusManager.getDescricaoStatus();
    }

    // ==================== MÉTODOS COMBATE ====================

    /**
     * Verifica se o personagem ainda está vivo.
     * 
     * @return true se vida > 0, false caso contrário
     */
    public boolean estaVivo() {
        return vida > 0;
    }

    /**
     * Aplica dano recebido do ataque de outro personagem.
     * 
     * Procedimento:
     * 1. Se morto, retorna 0 (nenhum dano recebido)
     * 2. 15% de chance de ESQUIVA completa (retorna 0)
     * 3. Se em DEFESA: dano reduzido para 50% do original
     * 4. Se PROTEGIDO (status): dano reduzido em 25% adicional
     * 5. Mínimo de 1 de dano garantido
     * 6. Reduz vida do personagem
     * 
     * @param dano Valor de dano a receber
     * @return Dano realmente recebido (afetado por defesa, status e esquiva)
     */
    public int receberDano(int dano) {
        // Se já está morto, não pode receber mais dano
        if (!estaVivo()) {
            return 0;
        }
        
        // 15% de chance de esquiva completa
        if (Math.random() < 0.15) {
            defendendo = false;
            ConsoleUI.printEffect("💨 " + nome + " ESQUIVOU!", ANSIColors.PURPLE);
            return 0;
        }
        
        // Se estava em defesa, reduz dano em 50%
        if (defendendo) {
            dano = Math.max(1, dano / 2);
            defendendo = false;  // Defesa é usada apenas para o próximo ataque
        }
        
        // Se PROTEGIDO: reduz dano adicional em 25%
        if (statusManager.getStatusAtual() == Status.PROTEGIDO) {
            dano = (int)(dano * statusManager.getModificadorDano());
        }
        
        // Garante mínimo de 1 de dano
        dano = Math.max(1, dano);
        vida = Math.max(0, vida - dano);
        return dano;
    }

    /**
     * Ativa a postura defensiva do personagem.
     * No próximo ataque recebido, o dano será reduzido em 50%.
     */
    public void defender() {
        defendendo = true;
    }

    /**
     * Método abstrato que deve ser implementado por todas as subclasses.
     * Define o ataque específico de cada classe com suas próprias características.
     * 
     * @param alvo O personagem que será atacado
     */
    public abstract void atacar(PersonagemBase alvo);

    // ==================== MÉTODOS PROGRESSÃO ====================

    /**
     * Adiciona experiência ao personagem e verifica se pode subir de nível.
     * 
     * @param xp Quantidade de XP a ganhar
     * @return Número de níveis subidos (0 se nenhum nível foi conquistado)
     * @see #subirNivel()
     */
    public int ganharExperiencia(int xp) {
        experiencia += xp;
        return subirNivel();
    }

    /**
     * Retorna a quantidade de XP necessária para o próximo nível.
     * 
     * @return XP restante para subir de nível
     */
    public int getXpParaSubir() {
        return xpParaSubir;
    }

    /**
     * Verifica se há experiência suficiente e sobe de nível se possível.
     * Quando sobe de nível:
     * - Vida máxima +5
     * - Energia máxima +3
     * - Ataque +2
     * - Defesa +2
     * - Restaura vida e energia totais
     * - Aumenta XP necessário em +30
     * 
     * @return Quantidade de níveis subidos
     */
    public int subirNivel() {
        int niveis = 0;
        while (experiencia >= xpParaSubir) {
            // Subtrai XP necessário e sobe nível
            experiencia -= xpParaSubir;
            nivel++;
            
            // Melhora atributos permanentes
            vidaMaxima += 5;
            energiaMaxima += 3;
            ataque += 2;
            defesa += 2;
            
            // Restaura vida e energia totais
            vida = vidaMaxima;
            energia = energiaMaxima;
            
            // Aumenta dificuldade do próximo nível
            xpParaSubir += 30;
            niveis++;
        }
        return niveis;
    }

    // ==================== MÉTODOS OURO ====================

    /**
     * Retorna a quantidade de ouro do personagem.
     * 
     * @return Quantidade de ouro
     */
    public int getOuro() {
        return ouro;
    }

    /**
     * Adiciona ouro ao personagem.
     * Utilizado após ganhar combate ou realizar transações na loja.
     * 
     * @param valor Quantidade de ouro a adicionar
     */
    public void adicionarOuro(int valor) {
        this.ouro = Math.max(0, this.ouro + valor);
    }

    /**
     * Remove ouro do personagem após uma compra na loja.
     * Valida se há ouro suficiente antes de remover.
     * 
     * @param valor Quantidade de ouro a gastar
     * @return true se a transação foi bem sucedida, false se ouro insuficiente
     */
    public boolean gastarOuro(int valor) {
        if (valor <= 0 || valor > this.ouro) {
            return false;
        }
        this.ouro -= valor;
        return true;
    }

    // ==================== MÉTODOS UTILIDADE ====================

    /**
     * Restaura totalmente a vida e energia do personagem.
     * Utilizado para resetar personagem após combate.
     */
    public void restaurarTotal() {
        vida = vidaMaxima;
        energia = energiaMaxima;
    }
}
