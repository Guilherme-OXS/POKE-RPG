package rpg;

/**
 * Interface que define o contrato para personagens que conseguem recuperar saúde e energia.
 * Qualquer classe que implemente esta interface DEVE fornecer sua própria implementação
 * de recuperação, permitindo diferentes taxas e mecanismos por personagem.
 * 
 * @author Projeto Integrador RPG
 * @see PersonagemBase
 * @see HabilidadeEspecial
 */
public interface Recuperavel {
    /**
     * Recupera saúde e energia do personagem.
     * A quantidade recuperada varia conforme a subclasse.
     * 
     * @see PersonagemBase#setVida(int)
     * @see PersonagemBase#setEnergia(int)
     */
    void recuperar();
}
