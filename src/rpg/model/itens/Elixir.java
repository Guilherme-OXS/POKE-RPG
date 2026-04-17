package rpg.model.itens;

import rpg.model.PersonagemBase;

public class Elixir extends Item {
    public Elixir() {
        super("Elixir Supremo", "Restaura vida e energia ao maximo.", 80);
    }

    @Override
    public String usar(PersonagemBase alvo) {
        int vidaGanha = alvo.getVidaMaxima() - alvo.getVidaAtual();
        int energiaGanha = alvo.getEnergiaMaxima() - alvo.getEnergiaAtual();
        alvo.setVidaAtual(alvo.getVidaMaxima());
        alvo.setEnergiaAtual(alvo.getEnergiaMaxima());
        return alvo.getNome() + " usou " + getNome() + " e restaurou " + vidaGanha
                + " de vida e " + energiaGanha + " de energia.";
    }

    @Override
    public Item copiar() {
        return new Elixir();
    }
}
