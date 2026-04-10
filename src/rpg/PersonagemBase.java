package rpg;

public abstract class PersonagemBase {
    private String nome;
    private int vida;
    private int vidaMaxima;
    private int ataque;
    private int defesa;
    private int energia;
    private int energiaMaxima;
    private int nivel;
    private int experiencia;
    private boolean defendendo;
    private int xpParaSubir;
    private int ouro;

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
    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public int getEnergia() {
        return energia;
    }

    public int getEnergiaMaxima() {
        return energiaMaxima;
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public boolean isDefendendo() {
        return defendendo;
    }

    public void setVida(int vida) {
        this.vida = Math.max(0, Math.min(vida, vidaMaxima));
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public void setEnergia(int energia) {
        this.energia = Math.max(0, Math.min(energia, energiaMaxima));
    }

    public void setEnergiaMaxima(int energiaMaxima) {
        this.energiaMaxima = energiaMaxima;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public int receberDano(int dano) {
        if (!estaVivo()) {
            return 0;
        }
        if (Math.random() < 0.15) {
            defendendo = false;
            return 0;
        }
        if (defendendo) {
            dano = Math.max(1, dano / 2);
            defendendo = false;
        }
        dano = Math.max(1, dano);
        vida = Math.max(0, vida - dano);
        return dano;
    }

    public void defender() {
        defendendo = true;
    }

    public int ganharExperiencia(int xp) {
        experiencia += xp;
        return subirNivel();
    }

    public int getXpParaSubir() {
        return xpParaSubir;
    }

    public int getOuro() {
        return ouro;
    }

    public void adicionarOuro(int valor) {
        this.ouro = Math.max(0, this.ouro + valor);
    }

    public boolean gastarOuro(int valor) {
        if (valor <= 0 || valor > this.ouro) {
            return false;
        }
        this.ouro -= valor;
        return true;
    }

    public int subirNivel() {
        int niveis = 0;
        while (experiencia >= xpParaSubir) {
            experiencia -= xpParaSubir;
            nivel++;
            vidaMaxima += 5;
            energiaMaxima += 3;
            ataque += 2;
            defesa += 2;
            vida = vidaMaxima;
            energia = energiaMaxima;
            xpParaSubir += 30;
            niveis++;
        }
        return niveis;
    }

    public void restaurarTotal() {
        vida = vidaMaxima;
        energia = energiaMaxima;
    }

    public abstract void atacar(PersonagemBase alvo);
}
