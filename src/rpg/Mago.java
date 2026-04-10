package rpg;

public class Mago extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    public Mago(String nome) {
        super(nome, 100, 32, 9, 108);
    }

    @Override
    public void atacar(PersonagemBase alvo) {
        int base = getAtaque() + (int) (Math.random() * 8);
        boolean critico = Math.random() < 0.25;
        if (critico) {
            base *= 2;
            Animacoes.importantEffect("💥 CRÍTICO MÁGICO!");
            Sons.beepCritical();
        } else {
            Sons.beepAttack();
        }
        int dano = Math.max(1, base - alvo.getDefesa() / 3);
        int danoRecebido = alvo.receberDano(dano);
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            Animacoes.shakeText("💢 DANO!");
            ConsoleUI.printEffect("Mago lança feitiço e causa " + danoRecebido + " de dano.", ANSIColors.RED);
        }
    }

    @Override
    public void usarHabilidade(PersonagemBase alvo) {
        if (getEnergia() < 25) {
            ConsoleUI.printEffect("Energia insuficiente para habilidade!", ANSIColors.RED);
            return;
        }
        setEnergia(getEnergia() - 25);
        Animacoes.actionAnimation("✨ HABILIDADE: Bola de Fogo");
        int dano = Math.max(1, (int) ((getAtaque() * 2.2) - alvo.getDefesa() / 4));
        int danoRecebido = alvo.receberDano(dano);
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            ConsoleUI.printEffect("✨ Bola de Fogo causa " + danoRecebido + " de dano.", ANSIColors.YELLOW);
            Sons.beepCritical();
        }
    }

    @Override
    public void recuperar() {
        Animacoes.actionAnimation("🍄 Recuperando...");
        setVida(getVida() + 14);
        setEnergia(getEnergia() + 25);
        ConsoleUI.printEffect("Mago recupera vida e energia.", ANSIColors.GREEN);
    }
}
