package rpg.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rpg.model.itens.Item;

public abstract class PersonagemBase {
    private final String nome;
    private final TipoPersonagem tipo;

    private int vidaMaxima;
    private int vidaAtual;
    private int ataque;
    private int defesa;

    private int energiaMaxima;
    private int energiaAtual;

    private int ouro;
    private boolean defesaAtiva;

    private final ArrayList<Item> inventario;

    protected PersonagemBase(
            String nome,
            TipoPersonagem tipo,
            int vidaMaxima,
            int ataque,
            int defesa,
            int energiaMaxima) {
        this.nome = nome == null || nome.trim().isEmpty() ? "SemNome" : nome.trim();
        this.tipo = tipo;
        this.vidaMaxima = Math.max(1, vidaMaxima);
        this.vidaAtual = this.vidaMaxima;
        this.ataque = Math.max(1, ataque);
        this.defesa = Math.max(0, defesa);
        this.energiaMaxima = Math.max(1, energiaMaxima);
        this.energiaAtual = this.energiaMaxima;
        this.ouro = 0;
        this.defesaAtiva = false;
        this.inventario = new ArrayList<>();
    }

    public abstract int atacar();

    public String getAsciiArte() {
        return "  O\n /|\\\n /_\\";
    }

    public String getNome() {
        return nome;
    }

    public TipoPersonagem getTipo() {
        return tipo;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = Math.max(1, vidaMaxima);
        if (vidaAtual > this.vidaMaxima) {
            vidaAtual = this.vidaMaxima;
        }
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(int vidaAtual) {
        if (vidaAtual < 0) {
            this.vidaAtual = 0;
            return;
        }
        this.vidaAtual = Math.min(vidaAtual, vidaMaxima);
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = Math.max(1, ataque);
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = Math.max(0, defesa);
    }

    public int getEnergiaMaxima() {
        return energiaMaxima;
    }

    public void setEnergiaMaxima(int energiaMaxima) {
        this.energiaMaxima = Math.max(1, energiaMaxima);
        if (energiaAtual > this.energiaMaxima) {
            energiaAtual = this.energiaMaxima;
        }
    }

    public int getEnergiaAtual() {
        return energiaAtual;
    }

    public void setEnergiaAtual(int energiaAtual) {
        if (energiaAtual < 0) {
            this.energiaAtual = 0;
            return;
        }
        this.energiaAtual = Math.min(energiaAtual, energiaMaxima);
    }

    public int getOuro() {
        return ouro;
    }

    public void setOuro(int ouro) {
        this.ouro = Math.max(0, ouro);
    }

    public void adicionarOuro(int valor) {
        if (valor > 0) {
            this.ouro += valor;
        }
    }

    public boolean gastarOuro(int valor) {
        if (valor <= 0 || valor > ouro) {
            return false;
        }
        ouro -= valor;
        return true;
    }

    public boolean estaVivo() {
        return vidaAtual > 0;
    }

    public boolean isDefesaAtiva() {
        return defesaAtiva;
    }

    public void ativarDefesa() {
        this.defesaAtiva = true;
    }

    public void limparDefesa() {
        this.defesaAtiva = false;
    }

    public int receberDano(int danoBruto) {
        int danoFinal = Math.max(1, danoBruto);

        if (defesaAtiva) {
            danoFinal = Math.max(1, (int) Math.ceil(danoFinal * 0.5));
            defesaAtiva = false;
        }

        setVidaAtual(vidaAtual - danoFinal);
        return danoFinal;
    }

    public void curarVida(int valor) {
        if (valor > 0) {
            setVidaAtual(vidaAtual + valor);
        }
    }

    public boolean gastarEnergia(int custo) {
        if (custo <= 0 || energiaAtual < custo) {
            return false;
        }
        energiaAtual -= custo;
        return true;
    }

    public void recuperarEnergia(int valor) {
        if (valor > 0) {
            setEnergiaAtual(energiaAtual + valor);
        }
    }

    public void adicionarItem(Item item) {
        if (item != null) {
            inventario.add(item);
        }
    }

    public Item removerItem(int indice) {
        return inventario.remove(indice);
    }

    public List<Item> getInventario() {
        return Collections.unmodifiableList(inventario);
    }

    public boolean temItens() {
        return !inventario.isEmpty();
    }

    public double getPercentualVida() {
        return vidaMaxima == 0 ? 0 : (vidaAtual * 100.0) / vidaMaxima;
    }

    public double getPercentualEnergia() {
        return energiaMaxima == 0 ? 0 : (energiaAtual * 100.0) / energiaMaxima;
    }
}
