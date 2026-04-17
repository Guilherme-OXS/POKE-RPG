package rpg.model.itens;

import rpg.model.PersonagemBase;

public abstract class Item {
    private final String nome;
    private final String descricao;
    private final int preco;

    protected Item(String nome, String descricao, int preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = Math.max(1, preco);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPreco() {
        return preco;
    }

    public abstract String usar(PersonagemBase alvo);

    public abstract Item copiar();
}
