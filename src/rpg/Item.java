package rpg;

/**
 * Classe que representa um item que pode ser comprado na loja ou usado em combate.
 * 
 * Tipos de itens:
 * - POÇÃO_VIDA: Restaura 50 de vida
 * - POÇÃO_ENERGIA: Restaura 30 de energia
 * - POÇÃO_TOTAL: Restaura vida e energia completamente
 * - ESCUDO_RESISTÊNCIA: Aumenta defesa em 5 permanentemente
 * - ESPADA_LETAL: Aumenta ataque em 8 permanentemente
 * - AMULETO_PROTEÇÃO: Ativa PROTEGIDO por 4 turnos
 * - ELIXIR_FURIA: Ativa FURIA por 3 turnos
 * - ANTÍDOTO: Remove debuffs e cura envenenamento
 * - MAPA_TESOURO: Adiciona 100 de ouro bônus
 * - LIVRO_SABEDORIA: Ganho extra de 50 XP
 * 
 * Cada item tem: nome, descrição, custo, efeito.
 * 
 * @author Projeto Integrador RPG
 * @see Main (onde os itens são vendidos)
 */
public class Item {
    /**
     * Enum dos tipos de itens disponíveis na loja (Balanceado para Dificuldade Aumentada).
     */
    public enum TipoItem {
        POÇÃO_VIDA("Poção de Vida", "Restaura 35 de vida", 40),             // Era 50 vida por 30 ouro
        POÇÃO_ENERGIA("Poção de Energia", "Restaura 20 de energia", 35),    // Era 30 energia por 25 ouro
        POÇÃO_TOTAL("Elixir Total", "Restaura vida e energia completos", 80),  // Era 60 ouro
        ESCUDO_RESISTÊNCIA("Escudo de Resistência", "Aumenta defesa permanentemente em 3", 100),  // Era +5 por 80 ouro
        ESPADA_LETAL("Espada Letal", "Aumenta ataque permanentemente em 5", 130),  // Era +8 por 100 ouro
        AMULETO_PROTEÇÃO("Amuleto de Proteção", "Proteção contra dano por 3 turnos", 90),  // Era 4 turnos por 70 ouro
        ELIXIR_FURIA("Elixir da Fúria", "Aumenta ataque em 20% por 2 turnos", 85),  // Era 30% por 3 turnos, 65 ouro
        ANTÍDOTO("Antídoto Universal", "Remove todos os debuffs e efeitos ruins", 70),  // Era 50 ouro
        MAPA_TESOURO("Mapa do Tesouro", "Encontra ouro escondido", 55),  // Era +100 ouro por40 ouro
        LIVRO_SABEDORIA("Livro de Sabedoria", "Conhecimento antigo: +30 XP", 75);  // Era +50 XP por 55 ouro
        
        private String nome;
        private String descricao;
        private int custo;
        
        /**
         * Construtor privado do enum.
         * @param nome Nome do item
         * @param descricao Descrição do efeito
         * @param custo Preço em ouro na loja
         */
        TipoItem(String nome, String descricao, int custo) {
            this.nome = nome;
            this.descricao = descricao;
            this.custo = custo;
        }
        
        public String getNome() { return nome; }
        public String getDescricao() { return descricao; }
        public int getCusto() { return custo; }
    }
    
    private TipoItem tipo;
    
    /**
     * Construtor que cria um item específico.
     * @param tipo Tipo de item a criar
     */
    public Item(TipoItem tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Usa o item no personagem passado como alvo.
     * Aplica todos os efeitos do item (cura reduzida, buff moderado, ganho de ouro/xp reduzido).
     * 
     * @param personagem Personagem que usará o item
     */
    public void usar(PersonagemBase personagem) {
        switch (tipo) {
            case POÇÃO_VIDA:
                personagem.setVida(personagem.getVida() + 35);  // Era 50
                ConsoleUI.printEffect("💊 " + personagem.getNome() + " usa " + tipo.nome + 
                                    " e recupera 35 de vida!", ANSIColors.GREEN);
                break;
                
            case POÇÃO_ENERGIA:
                personagem.setEnergia(personagem.getEnergia() + 20);  // Era 30
                ConsoleUI.printEffect("⚡ " + personagem.getNome() + " usa " + tipo.nome + 
                                    " e recupera 20 de energia!", ANSIColors.BLUE);
                break;
                
            case POÇÃO_TOTAL:
                personagem.restaurarTotal();
                ConsoleUI.printEffect("✨ " + personagem.getNome() + " usa " + tipo.nome + 
                                    " e se sente completamente restaurado!", ANSIColors.YELLOW);
                break;
                
            case ESCUDO_RESISTÊNCIA:
                personagem.setDefesa(personagem.getDefesa() + 3);  // Era 5
                ConsoleUI.printEffect("🛡️ " + personagem.getNome() + " equipa " + tipo.nome + 
                                    " (+3 defesa permanente!)", ANSIColors.CYAN);
                break;
                
            case ESPADA_LETAL:
                personagem.setAtaque(personagem.getAtaque() + 5);  // Era 8
                ConsoleUI.printEffect("⚔️ " + personagem.getNome() + " equipa " + tipo.nome + 
                                    " (+5 ataque permanente!)", ANSIColors.RED);
                break;
                
            case AMULETO_PROTEÇÃO:
                personagem.aplicarStatus(Status.PROTEGIDO, 3);  // Era 4 turnos
                ConsoleUI.printEffect("🛡️ " + personagem.getNome() + " ativa " + tipo.nome + 
                                    " - proteção contra dano por 3 turnos!", ANSIColors.BLUE);
                break;
                
            case ELIXIR_FURIA:
                personagem.aplicarStatus(Status.FURIA, 2);  // Era 3 turnos
                ConsoleUI.printEffect("⚡ " + personagem.getNome() + " bebe " + tipo.nome + 
                                    " - fúria aumenta ataque por 2 turnos!", ANSIColors.YELLOW);
                break;
                
            case ANTÍDOTO:
                if (personagem.getStatusManager().getStatusAtual() != Status.NENHUM) {
                    personagem.getStatusManager().limparStatus();
                    ConsoleUI.printEffect("✨ " + personagem.getNome() + " usa " + tipo.nome + 
                                        " - todos os efeitos foram removidos!", ANSIColors.GREEN);
                } else {
                    ConsoleUI.printEffect("⚠️ " + personagem.getNome() + " não tem nenhum efeito ativo!", ANSIColors.YELLOW);
                }
                break;
                
            case MAPA_TESOURO:
                int random = (int)(Math.random() * 100);
                personagem.adicionarOuro(random);  // Era 20 (nesse caso aumentei pois está muito caro agora)
                ConsoleUI.printEffect("💰 " + personagem.getNome() + " usa " + tipo.nome + 
                                    " e encontra " + random + " de ouro!", ANSIColors.YELLOW);
                break;
                
            case LIVRO_SABEDORIA:
                personagem.ganharExperiencia(30);  // Era 50
                ConsoleUI.printEffect("📖 " + personagem.getNome() + " lê " + tipo.nome + 
                                    " e ganha 30 de XP!", ANSIColors.BLUE);
                break;
        }
    }
    
    /**
     * Retorna o tipo de item.
     * @return TipoItem
     */
    public TipoItem getTipo() {
        return tipo;
    }
    
    /**
     * Retorna informações formatadas do item para exibição na loja.
     * @return String com nome, preço e descrição
     */
    @Override
    public String toString() {
        return tipo.nome + " [" + tipo.custo + "g] - " + tipo.descricao;
    }
}
