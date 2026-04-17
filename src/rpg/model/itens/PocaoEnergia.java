package rpg.model.itens;

import rpg.model.PersonagemBase;

public class PocaoEnergia extends Item {
    private final int energia;

    public PocaoEnergia() {
        this(30);
    }

    public PocaoEnergia(int energia) {
        super("Pocao de Energia", "Recupera energia de combate.", 30);
        this.energia = Math.max(1, energia);
    }

    @Override
    public String usar(PersonagemBase alvo) {
        int energiaAntes = alvo.getEnergiaAtual();
        alvo.recuperarEnergia(energia);
        int ganho = alvo.getEnergiaAtual() - energiaAntes;
        return alvo.getNome() + " usou " + getNome() + " e recuperou " + ganho + " de energia.";
    }

    @Override
    public Item copiar() {
        return new PocaoEnergia(energia);
    }
}
