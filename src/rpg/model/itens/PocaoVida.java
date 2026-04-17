package rpg.model.itens;

import rpg.model.PersonagemBase;

public class PocaoVida extends Item {
    private final int cura;

    public PocaoVida() {
        this(35);
    }

    public PocaoVida(int cura) {
        super("Pocao de Vida", "Recupera vida imediatamente.", 35);
        this.cura = Math.max(1, cura);
    }

    @Override
    public String usar(PersonagemBase alvo) {
        int vidaAntes = alvo.getVidaAtual();
        alvo.curarVida(cura);
        int ganho = alvo.getVidaAtual() - vidaAntes;
        return alvo.getNome() + " usou " + getNome() + " e recuperou " + ganho + " de vida.";
    }

    @Override
    public Item copiar() {
        return new PocaoVida(cura);
    }
}
