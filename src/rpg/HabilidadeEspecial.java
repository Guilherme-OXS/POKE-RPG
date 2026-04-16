package rpg;

/**
 * Interface que define o contrato para personagens que possuem habilidades especiais.
 * Qualquer classe que implemente esta interface DEVE fornecer uma implementação
 * da habilidade especial com alto custo de energia mas maior dano.
 * 
 * @author Projeto Integrador RPG
 * @see PersonagemBase
 * @see Recuperavel
 */
public interface HabilidadeEspecial {
    /**
     * Executa a habilidade especial do personagem contra um alvo.
     * A habilidade consome energia (custo varia por classe) e causa dano ampliado.
     * 
     * @param alvo O personagem que sofrerá o ataque especial
     * @see PersonagemBase#setEnergia(int)
     * @see PersonagemBase#receberDano(int)
     */
    void usarHabilidade(PersonagemBase alvo);
}
