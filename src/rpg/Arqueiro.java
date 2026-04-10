package rpg;

public class Arqueiro extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    public Arqueiro(String nome) {
        super(nome, 115, 28, 11, 80);
    }

    @Override
    public void atacar(PersonagemBase alvo) {
        int base = getAtaque() + (int) (Math.random() * 10);
        boolean critico = Math.random() < 0.30;
        if (critico) {
            base *= 2;
            Animacoes.importantEffect("💥 Tiro crítico!");
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
            ConsoleUI.printEffect("Arqueiro acerta flecha e causa " + danoRecebido + " de dano.", ANSIColors.RED);
        }
    }

    @Override
    public void usarHabilidade(PersonagemBase alvo) {
        if (getEnergia() < 20) {
            ConsoleUI.printEffect("Energia insuficiente para habilidade!", ANSIColors.RED);
            return;
        }
        setEnergia(getEnergia() - 20);
        Animacoes.actionAnimation("✨ HABILIDADE: Chuva de Flechas");
        int totalDano = 0;
        for (int i = 0; i < 3; i++) {
            int dano = Math.max(1, (int) ((getAtaque() * 0.9) - alvo.getDefesa() / 5));
            totalDano += alvo.receberDano(dano);
        }
        if (totalDano == 0) {
            ConsoleUI.printEffect("💨 ESQUIVOU!", ANSIColors.PURPLE);
        } else {
            ConsoleUI.printEffect("✨ Chuva de Flechas causa " + totalDano + " de dano.", ANSIColors.YELLOW);
            Sons.beepCritical();
        }
    }

    @Override
    public void recuperar() {
        Animacoes.actionAnimation("🍃 Recuperando...");
        setVida(getVida() + 16);
        setEnergia(getEnergia() + 22);
        ConsoleUI.printEffect("Arqueiro recupera vida e energia.", ANSIColors.GREEN);
    }
}
