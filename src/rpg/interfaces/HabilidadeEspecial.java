package rpg.interfaces;

import rpg.model.PersonagemBase;

public interface HabilidadeEspecial {
    int usarHabilidade(PersonagemBase alvo);
    int getCustoHabilidade();

    String getNomeHabilidade();

    String descreverRequisitosHabilidade();

    String descreverProgressoHabilidade(PersonagemBase alvo);

    boolean podeUsarHabilidade(PersonagemBase alvo);
}
