package rpg.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;
import rpg.model.itens.Item;
import rpg.utils.AsciiAnimation;
import rpg.utils.AsciiAnimation.TipoAcaoAnimada;
import rpg.utils.ConsoleUI;

public class Combate {
    private static final String[] MENU_TURNO = {
            "1. Atacar",
            "2. Defender (50% menos dano no proximo golpe)",
            "3. Habilidade Especial",
            "4. Recuperar",
            "5. Usar Item"
    };

    private final Scanner scanner;
    private final ArrayList<String> historicoAcoes;
    private final String[] ultimasAcoes;
    private int indiceCircular;

    public Combate(Scanner scanner) {
        this.scanner = scanner;
        this.historicoAcoes = new ArrayList<>();
        this.ultimasAcoes = new String[6];
        this.indiceCircular = 0;
    }

    public List<String> getHistoricoAcoes() {
        return Collections.unmodifiableList(historicoAcoes);
    }

    public boolean iniciar(PersonagemBase heroi, PersonagemBase inimigo) {
        registrarAcao("Um novo duelo comecou: " + heroi.getNome() + " vs " + inimigo.getNome());
        boolean turnoJogador = true;

        while (heroi.estaVivo() && inimigo.estaVivo()) {
            ConsoleUI.limparTela();
            ConsoleUI.mostrarBannerPrincipal();
            ConsoleUI.mostrarCampoBatalha(heroi, inimigo);
            ConsoleUI.mostrarStatus(heroi, inimigo);
            ConsoleUI.mostrarHistorico(getUltimasAcoesOrdenadas());

            if (turnoJogador) {
                executarTurnoJogador(heroi, inimigo);
            } else {
                executarTurnoInimigo(inimigo, heroi);
            }

            turnoJogador = !turnoJogador;
            ConsoleUI.pausar(650);
        }

        if (heroi.estaVivo()) {
            registrarAcao("Vitoria! " + inimigo.getNome() + " foi derrotado.");
        } else {
            registrarAcao("Derrota... " + heroi.getNome() + " caiu em combate.");
        }

        ConsoleUI.limparTela();
        ConsoleUI.mostrarBannerPrincipal();
        ConsoleUI.mostrarStatus(heroi, inimigo);
        ConsoleUI.mostrarHistorico(getUltimasAcoesOrdenadas());
        ConsoleUI.pausar(1300);
        return heroi.estaVivo();
    }

    private void executarTurnoJogador(PersonagemBase heroi, PersonagemBase inimigo) {
        boolean acaoConcluida = false;
        while (!acaoConcluida && heroi.estaVivo() && inimigo.estaVivo()) {
            mostrarGuiaHabilidade(heroi, inimigo);
            mostrarMenuJogador();
            int opcao = lerInteiro("Escolha sua acao: ", 1, 5);

            switch (opcao) {
                case 1:
                    animarDuelo(heroi, inimigo, TipoAcaoAnimada.ATAQUE, "Ataque");
                    int poderAtaque = heroi.atacar();
                    int dano = aplicarDano(heroi, inimigo, poderAtaque);
                    String msgAtaque = heroi.getNome() + " atacou e causou " + dano + " de dano.";
                    registrarAcao(msgAtaque);
                    ConsoleUI.mensagemSucesso(msgAtaque);
                    acaoConcluida = true;
                    break;
                case 2:
                    animarAcao(heroi, TipoAcaoAnimada.DEFESA, "Defesa");
                    heroi.ativarDefesa();
                    heroi.recuperarEnergia((int) Math.ceil(heroi.getEnergiaMaxima() * 0.30));
                    String msgDefesa = heroi.getNome() + " levantou defesa e reduzira o proximo dano em 50%.";
                    registrarAcao(msgDefesa);
                    ConsoleUI.mensagemInfo(msgDefesa);
                    acaoConcluida = true;
                    break;
                case 3:
                    acaoConcluida = tentarHabilidade(heroi, inimigo);
                    break;
                case 4:
                    acaoConcluida = tentarRecuperacao(heroi);
                    break;
                case 5:
                    acaoConcluida = usarItemNoTurno(heroi);
                    break;
                default:
                    ConsoleUI.mensagemErro("Opcao invalida.");
                    break;
            }
        }
    }

    private boolean tentarHabilidade(PersonagemBase atacante, PersonagemBase alvo) {
        if (!(atacante instanceof HabilidadeEspecial)) {
            ConsoleUI.mensagemAlerta("Esse personagem nao possui habilidade especial.");
            return false;
        }

        HabilidadeEspecial habilidade = (HabilidadeEspecial) atacante;
        if (!habilidade.podeUsarHabilidade(alvo)) {
            registrarAcao(atacante.getNome() + " ainda nao cumpriu os requisitos de " + habilidade.getNomeHabilidade() + ".");
            ConsoleUI.mensagemAlerta("Habilidade indisponivel: " + habilidade.getNomeHabilidade());
            ConsoleUI.mensagemInfo("Requisitos: " + habilidade.descreverRequisitosHabilidade());
            ConsoleUI.mensagemInfo("Progresso atual: " + habilidade.descreverProgressoHabilidade(alvo));
            return false;
        }

        int danoHabilidade = habilidade.usarHabilidade(alvo);
        if (danoHabilidade < 0) {
            String msg = "Condicoes da habilidade nao atendidas no momento.";
            registrarAcao(atacante.getNome() + " falhou ao tentar habilidade especial.");
            ConsoleUI.mensagemAlerta(msg);
            ConsoleUI.mensagemInfo("Progresso atual: " + habilidade.descreverProgressoHabilidade(alvo));
            return false;
        }

        animarDuelo(atacante, alvo, TipoAcaoAnimada.HABILIDADE, "Habilidade Especial");
        int danoFinal = aplicarDano(atacante, alvo, danoHabilidade);
        String msg = atacante.getNome() + " usou " + habilidade.getNomeHabilidade() + " e causou " + danoFinal + " de dano.";
        registrarAcao(msg);
        ConsoleUI.mensagemSucesso(msg);
        return true;
    }

    private boolean tentarRecuperacao(PersonagemBase personagem) {
        if (!(personagem instanceof Recuperavel)) {
            ConsoleUI.mensagemAlerta("Esse personagem nao possui tecnica de recuperacao.");
            return false;
        }

        Recuperavel recuperavel = (Recuperavel) personagem;
        animarAcao(personagem, TipoAcaoAnimada.RECUPERAR, "Recuperacao");
        String msg = recuperavel.recuperar();
        registrarAcao(msg);
        ConsoleUI.mensagemInfo(msg);
        return true;
    }

    private void executarTurnoInimigo(PersonagemBase inimigo, PersonagemBase heroi) {
        HabilidadeEspecial habilidade = inimigo instanceof HabilidadeEspecial
                ? (HabilidadeEspecial) inimigo
                : null;
        boolean podeUsarHabilidade = habilidade != null && habilidade.podeUsarHabilidade(heroi);

        int danoAtaqueEstimado = estimarDano(inimigo, heroi, inimigo.getAtaque());
        int danoHabilidadeEstimado = Math.max(danoAtaqueEstimado + 8, (int) Math.round(danoAtaqueEstimado * 1.65));

        boolean finalizaComAtaque = heroi.getVidaAtual() <= danoAtaqueEstimado;
        boolean finalizaComHabilidade = podeUsarHabilidade && heroi.getVidaAtual() <= danoHabilidadeEstimado;

        if (finalizaComHabilidade) {
            tentarHabilidade(inimigo, heroi);
            return;
        }

        if (finalizaComAtaque) {
            executarAtaqueBasico(inimigo, heroi);
            return;
        }

        int vidaFaltante = inimigo.getVidaMaxima() - inimigo.getVidaAtual();
        int energiaFaltante = inimigo.getEnergiaMaxima() - inimigo.getEnergiaAtual();

        boolean vidaCritica = inimigo.getPercentualVida() <= 28.0;
        boolean vidaEmRisco = inimigo.getPercentualVida() <= 40.0 && heroi.getPercentualVida() > 20.0;
        boolean energiaMuitoBaixa = inimigo.getPercentualEnergia() <= 22.0;
        boolean recuperacaoValiosa = (vidaFaltante >= 12 && (vidaCritica || vidaEmRisco))
                || (energiaMuitoBaixa && energiaFaltante >= 20 && !podeUsarHabilidade);

        boolean recuperacaoDesnecessaria = vidaFaltante < 8 && energiaFaltante < 12;
        if (recuperacaoValiosa && !recuperacaoDesnecessaria) {
            if (tentarRecuperacao(inimigo)) {
                return;
            }
        }

        boolean deveDefender = inimigo.getPercentualVida() <= 35.0
                && !recuperacaoValiosa
                && heroi.getEnergiaAtual() >= 20;
        if (deveDefender) {
            executarDefesaInimigo(inimigo);
            return;
        }

        boolean usaHabilidadePorPressao = podeUsarHabilidade
                && (heroi.getPercentualVida() > 30.0 || inimigo.getPercentualVida() < 50.0);
        if (usaHabilidadePorPressao) {
            boolean usou = tentarHabilidade(inimigo, heroi);
            if (usou) {
                return;
            }
        }

        int escolha = ThreadLocalRandom.current().nextInt(100);
        if (podeUsarHabilidade && escolha < 22) {
            boolean usou = tentarHabilidade(inimigo, heroi);
            if (usou) {
                return;
            }
        }

        if (escolha > 93 && inimigo.getPercentualVida() < 55.0) {
            executarDefesaInimigo(inimigo);
            return;
        }

        executarAtaqueBasico(inimigo, heroi);
    }

    private boolean usarItemNoTurno(PersonagemBase heroi) {
        if (!heroi.temItens()) {
            ConsoleUI.mensagemAlerta("Seu inventario esta vazio.");
            return false;
        }

        while (true) {
            try {
                List<Item> itens = heroi.getInventario();
                ConsoleUI.mostrarInventario(itens);

                int escolha = lerInteiro("Item: ", 0, itens.size());
                if (escolha == 0) {
                    return false;
                }

                animarAcao(heroi, TipoAcaoAnimada.ITEM, "Item");
                Item itemUsado = heroi.removerItem(escolha - 1);
                String msg = itemUsado.usar(heroi);
                registrarAcao(msg);
                ConsoleUI.mensagemSucesso(msg);
                return true;
            } catch (IndexOutOfBoundsException e) {
                ConsoleUI.mensagemErro("Indice invalido no inventario.");
            } catch (Exception e) {
                ConsoleUI.mensagemErro("Erro ao usar item: " + e.getMessage());
            } finally {
                System.out.flush();
            }
        }
    }

    private int aplicarDano(PersonagemBase atacante, PersonagemBase alvo, int poderAtaque) {
        int danoBase = Math.max(1, poderAtaque - alvo.getDefesa());
        double multiplicador = calcularMultiplicador(atacante.getTipo(), alvo.getTipo());
        int danoComVantagem = Math.max(1, (int) Math.round(danoBase * multiplicador));
        return alvo.receberDano(danoComVantagem);
    }

    private double calcularMultiplicador(TipoPersonagem atacante, TipoPersonagem alvo) {
        if (atacante == TipoPersonagem.GUERREIRO && alvo == TipoPersonagem.GOBLIN) {
            return 1.25;
        }
        if (atacante == TipoPersonagem.MAGO && alvo == TipoPersonagem.ORC) {
            return 1.30;
        }
        if (atacante == TipoPersonagem.ARQUEIRO && alvo == TipoPersonagem.DRAGAO) {
            return 1.35;
        }

        if (atacante == TipoPersonagem.ORC && alvo == TipoPersonagem.GUERREIRO) {
            return 1.15;
        }
        if (atacante == TipoPersonagem.GOBLIN && alvo == TipoPersonagem.MAGO) {
            return 1.15;
        }
        if (atacante == TipoPersonagem.DRAGAO && alvo == TipoPersonagem.ARQUEIRO) {
            return 1.20;
        }

        return 1.0;
    }

    private void mostrarMenuJogador() {
        ConsoleUI.mostrarPainelAcoes("ACOES DO TURNO", MENU_TURNO);
    }

    private void mostrarGuiaHabilidade(PersonagemBase personagem, PersonagemBase alvo) {
        if (!(personagem instanceof HabilidadeEspecial)) {
            return;
        }

        HabilidadeEspecial habilidade = (HabilidadeEspecial) personagem;
        ConsoleUI.mensagemInfo("Habilidade: " + habilidade.getNomeHabilidade()
                + " (Custo: " + habilidade.getCustoHabilidade() + " EN)");
        ConsoleUI.mensagemInfo("Requisitos: " + habilidade.descreverRequisitosHabilidade());
        ConsoleUI.mensagemInfo("Progresso: " + habilidade.descreverProgressoHabilidade(alvo));
        if (habilidade.podeUsarHabilidade(alvo)) {
            ConsoleUI.mensagemSucesso("Status da habilidade: PRONTA.");
        } else {
            ConsoleUI.mensagemAlerta("Status da habilidade: ainda NAO pronta.");
        }
    }

    private void registrarAcao(String mensagem) {
        historicoAcoes.add(mensagem);
        ultimasAcoes[indiceCircular] = mensagem;
        indiceCircular = (indiceCircular + 1) % ultimasAcoes.length;
    }

    private int lerInteiro(String mensagem, int min, int max) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            try {
                int valor = Integer.parseInt(entrada.trim());
                if (valor < min || valor > max) {
                    throw new IllegalArgumentException("fora do intervalo");
                }
                return valor;
            } catch (NumberFormatException e) {
                ConsoleUI.mensagemErro("Entrada invalida. Digite um numero inteiro.");
            } catch (IllegalArgumentException e) {
                ConsoleUI.mensagemAlerta("Opcao invalida. Escolha entre " + min + " e " + max + ".");
            } finally {
                System.out.flush();
            }
        }
    }

    private String[] getUltimasAcoesOrdenadas() {
        String[] ordenadas = new String[ultimasAcoes.length];
        int posicao = 0;

        for (int i = 0; i < ultimasAcoes.length; i++) {
            int indice = (indiceCircular + i) % ultimasAcoes.length;
            String acao = ultimasAcoes[indice];
            if (acao != null) {
                ordenadas[posicao] = acao;
                posicao++;
            }
        }

        return ordenadas;
    }

    private void animarAcao(PersonagemBase personagem, TipoAcaoAnimada acao, String descricao) {
        String[] quadros = AsciiAnimation.obterAnimacao(personagem.getTipo(), acao);
        int tempo = AsciiAnimation.obterTempo(acao);
        AsciiAnimation.rodarAnimacao(quadros, tempo, personagem.getNome() + " - " + descricao);
    }

    private void animarDuelo(PersonagemBase atacante, PersonagemBase alvo, TipoAcaoAnimada acao, String descricao) {
        AsciiAnimation.rodarAnimacaoDuelo(
                atacante.getTipo(),
                alvo.getTipo(),
                acao,
                atacante.getNome() + " -> " + alvo.getNome() + " | " + descricao);
    }

    private int estimarDano(PersonagemBase atacante, PersonagemBase alvo, int poderAtaque) {
        int danoBase = Math.max(1, poderAtaque - alvo.getDefesa());
        double multiplicador = calcularMultiplicador(atacante.getTipo(), alvo.getTipo());
        return Math.max(1, (int) Math.round(danoBase * multiplicador));
    }

    private void executarAtaqueBasico(PersonagemBase atacante, PersonagemBase alvo) {
        animarDuelo(atacante, alvo, TipoAcaoAnimada.ATAQUE, "Ataque");
        int poderAtaque = atacante.atacar();
        int dano = aplicarDano(atacante, alvo, poderAtaque);
        String msg = atacante.getNome() + " atacou e causou " + dano + " de dano.";
        registrarAcao(msg);
        if (atacante.getTipo() == TipoPersonagem.GUERREIRO
                || atacante.getTipo() == TipoPersonagem.MAGO
                || atacante.getTipo() == TipoPersonagem.ARQUEIRO) {
            ConsoleUI.mensagemSucesso(msg);
        } else {
            ConsoleUI.mensagemErro(msg);
        }
    }

    private void executarDefesaInimigo(PersonagemBase inimigo) {
        animarAcao(inimigo, TipoAcaoAnimada.DEFESA, "Defesa");
        inimigo.ativarDefesa();
        inimigo.recuperarEnergia((int) Math.ceil(inimigo.getEnergiaMaxima() * 0.20));
        String msg = inimigo.getNome() + " se protegeu e preparou o contra-ataque.";
        registrarAcao(msg);
        ConsoleUI.mensagemInfo(msg);
    }
}
