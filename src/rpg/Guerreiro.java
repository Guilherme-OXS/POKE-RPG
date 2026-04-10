package rpg;

public class Guerreiro extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    public Guerreiro(String nome) {
        super(nome, 156, 26, 16, 66);
    }

    @Override
    public void atacar(PersonagemBase alvo) {
        int base = getAtaque() + (int) (Math.random() * 6);
        boolean critico = Math.random() < 0.20;
        if (critico) {
            base *= 2;
            Animacoes.importantEffect("💥 CRÍTICO!");
            Sons.beepCritical();
        } else {
            Sons.beepAttack();
        }
        int dano = Math.max(1, base - alvo.getDefesa() / 2);
        int danoRecebido = alvo.receberDano(dano);
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            Animacoes.shakeText("💢 DANO!");
            ConsoleUI.printEffect("Guerreiro causa " + danoRecebido + " de dano.", ANSIColors.RED);
        }
    }

    @Override
    public void usarHabilidade(PersonagemBase alvo) {
        if (getEnergia() < 20) {
            ConsoleUI.printEffect("Energia insuficiente para habilidade!", ANSIColors.RED);
            return;
        }
        setEnergia(getEnergia() - 20);
        Animacoes.actionAnimation("✨ HABILIDADE: Impacto de Aço");
        int dano = Math.max(1, (int) ((getAtaque() * 1.8) - alvo.getDefesa() / 2));
        int danoRecebido = alvo.receberDano(dano);
        if (danoRecebido == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            ConsoleUI.printEffect("✨ Impacto de Aço causa " + danoRecebido + " de dano.", ANSIColors.YELLOW);
            Sons.beepCritical();
        }
    }

    @Override
    public void recuperar() {
        Animacoes.actionAnimation("🍃 Recuperando...");
        setVida(getVida() + 18);
        setEnergia(getEnergia() + 20);
        ConsoleUI.printEffect("Guerreiro recupera vida e energia.", ANSIColors.GREEN);
    }
}
