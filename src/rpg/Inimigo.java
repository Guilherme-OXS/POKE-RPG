package rpg;

public class Inimigo extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    private final String habilidadeNome;
    private final String[] sprite;
    private final int xpDrop;
    private final int ouroDrop;

    public Inimigo(String nome, int vidaMaxima, int ataque, int defesa, int energiaMaxima, String habilidadeNome, String[] sprite, int xpDrop, int ouroDrop, int nivel) {
        super(nome, vidaMaxima, ataque, defesa, energiaMaxima);
        this.habilidadeNome = habilidadeNome;
        this.sprite = sprite;
        this.xpDrop = xpDrop;
        this.ouroDrop = ouroDrop;
        setNivel(nivel);
    }

    public String[] getSprite() {
        return sprite;
    }

    public int getXpDrop() {
        return xpDrop;
    }

    public int getOuroDrop() {
        return ouroDrop;
    }

    @Override
    public void atacar(PersonagemBase alvo) {
        int base = getAtaque() + (int) (Math.random() * 8);
        boolean critico = Math.random() < 0.18;
        if (critico) {
            base *= 2;
            Animacoes.importantEffect("💥 CRÍTICO INIMIGO!");
            Sons.beepCritical();
        } else {
            Sons.beepAttack();
        }
        int dano = Math.max(1, base - alvo.getDefesa() / 2);
        int danoRecebido = alvo.receberDano(dano);
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 O inimigo errou o ataque!", ANSIColors.PURPLE);
        } else {
            Animacoes.shakeText("💢 DANO!");
            ConsoleUI.printEffect(getNome() + " causa " + danoRecebido + " de dano.", ANSIColors.RED);
        }
    }

    @Override
    public void usarHabilidade(PersonagemBase alvo) {
        if (getEnergia() < 18) {
            atacar(alvo);
            return;
        }
        setEnergia(getEnergia() - 18);
        Animacoes.actionAnimation("✨ HABILIDADE: " + habilidadeNome);
        int dano = Math.max(1, (int) ((getAtaque() * 1.9) - alvo.getDefesa() / 3));
        int danoRecebido = alvo.receberDano(dano);
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 O inimigo falhou a habilidade!", ANSIColors.PURPLE);
        } else {
            ConsoleUI.printEffect(getNome() + " usa " + habilidadeNome + " e causa " + danoRecebido + " de dano.", ANSIColors.YELLOW);
            Sons.beepCritical();
        }
    }

    @Override
    public void recuperar() {
        Animacoes.actionAnimation("🍄 Inimigo recuperando...");
        setVida(getVida() + 14);
        setEnergia(getEnergia() + 18);
        ConsoleUI.printEffect(getNome() + " recupera vida e energia.", ANSIColors.GREEN);
    }

    public void decidirAcao(PersonagemBase jogador) {
        double motivacao = Math.random();
        if (getVida() < getVidaMaxima() * 0.3 && motivacao < 0.55) {
            defender();
            ConsoleUI.printEffect(getNome() + " está se defendendo!", ANSIColors.PURPLE);
            return;
        }
        if (getEnergia() >= 18 && motivacao < 0.4) {
            usarHabilidade(jogador);
            return;
        }
        if (getVida() < getVidaMaxima() * 0.5 && getEnergia() >= 10 && motivacao < 0.65) {
            recuperar();
            return;
        }
        if (motivacao < 0.25) {
            defender();
            ConsoleUI.printEffect(getNome() + " assume postura defensiva.", ANSIColors.PURPLE);
        } else {
            atacar(jogador);
        }
    }
}
