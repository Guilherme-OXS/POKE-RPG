package rpg.model.inimigos;

import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;

public class ChefeMisterioso extends PersonagemBase implements HabilidadeEspecial, Recuperavel {
    private final String habilidadeNome;
    private final String asciiArte;
    private final int custoHabilidade;
    private final double multiplicadorHabilidade;
    private final int bonusHabilidade;
    private final int cura;
    private final int energiaRecuperada;
    private final String textoRecuperacao;

    public ChefeMisterioso(
            String nome,
            TipoPersonagem tipo,
            String habilidadeNome,
            String asciiArte,
            int vidaMaxima,
            int ataque,
            int defesa,
            int energiaMaxima,
            int custoHabilidade,
            double multiplicadorHabilidade,
            int bonusHabilidade,
            int cura,
            int energiaRecuperada,
            String textoRecuperacao) {
        super(nome, tipo == null ? TipoPersonagem.CHEFE_REI_DEMONIO : tipo, vidaMaxima, ataque, defesa, energiaMaxima);
        this.habilidadeNome = habilidadeNome;
        this.asciiArte = asciiArte;
        this.custoHabilidade = Math.max(1, custoHabilidade);
        this.multiplicadorHabilidade = Math.max(1.3, multiplicadorHabilidade);
        this.bonusHabilidade = Math.max(0, bonusHabilidade);
        this.cura = Math.max(1, cura);
        this.energiaRecuperada = Math.max(1, energiaRecuperada);
        this.textoRecuperacao = textoRecuperacao == null ? "{chefe} recuperou o folego." : textoRecuperacao;
    }

    @Override
    public int atacar() {
        return getAtaque() + ThreadLocalRandom.current().nextInt(3, 13);
    }

    @Override
    public int usarHabilidade(PersonagemBase alvo) {
        if (!podeUsarHabilidade(alvo)) {
            return -1;
        }

        gastarEnergia(getCustoHabilidade());
        return (int) Math.round(getAtaque() * multiplicadorHabilidade) + bonusHabilidade;
    }

    @Override
    public int getCustoHabilidade() {
        return custoHabilidade;
    }

    @Override
    public String getNomeHabilidade() {
        return habilidadeNome;
    }

    @Override
    public String descreverRequisitosHabilidade() {
        return "Energia >= " + getCustoHabilidade()
                + ", Alvo vivo, Contexto tatico ativo (vida <= 85% ou energia >= 65%)";
    }

    @Override
    public String descreverProgressoHabilidade(PersonagemBase alvo) {
        int faltaEnergia = Math.max(0, getCustoHabilidade() - getEnergiaAtual());
        boolean alvoVivo = alvo != null && alvo.estaVivo();
        boolean contextoPorVida = getPercentualVida() <= 85.0;
        boolean contextoPorEnergia = getPercentualEnergia() >= 65.0;

        String energiaTexto = faltaEnergia == 0 ? "Energia OK" : "Faltam " + faltaEnergia + " de energia";
        String alvoTexto = alvoVivo ? "Alvo confirmado" : "Alvo invalido";
        String contextoTexto = (contextoPorVida || contextoPorEnergia)
                ? "Contexto tatico ativo"
                : "Contexto tatico inativo";

        return energiaTexto + " | " + alvoTexto + " | " + contextoTexto;
    }

    @Override
    public boolean podeUsarHabilidade(PersonagemBase alvo) {
        boolean energiaOk = getEnergiaAtual() >= getCustoHabilidade();
        boolean alvoVivo = alvo != null && alvo.estaVivo();
        boolean contextoFavoravel = getPercentualVida() <= 85.0 || getPercentualEnergia() >= 65.0;
        return energiaOk && alvoVivo && contextoFavoravel;
    }

    @Override
    public String recuperar() {
        curarVida(cura);
        recuperarEnergia(energiaRecuperada);
        return textoRecuperacao.replace("{chefe}", getNome());
    }

    @Override
    public String getAsciiArte() {
        return asciiArte;
    }

    public String getHabilidadeNome() {
        return habilidadeNome;
    }
}