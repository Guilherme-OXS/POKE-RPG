package rpg.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import rpg.interfaces.HabilidadeEspecial;
import rpg.interfaces.Recuperavel;
import rpg.model.PersonagemBase;
import rpg.model.TipoPersonagem;
import rpg.model.itens.Item;
import rpg.model.inimigos.Goku;
import rpg.utils.AsciiAnimation;
import rpg.utils.AsciiAnimation.TipoAcaoAnimada;
import rpg.utils.ConsoleUI;

public class Combate {
    private static final int CHANCE_EVENTO_GOKU = 1;
    private static boolean eventoGokuJaUsado = false;
    private static final int LIMITE_HISTORICO_VISUAL = 6;

    private static final String[] MENU_TURNO = {
            "1. Atacar",
            "2. Defender (50% menos dano no proximo golpe)",
            "3. Habilidade Especial",
            "4. Recuperar",
            "5. Usar Item"
    };

    private final Scanner scanner;
    private final ArrayList<String> historicoAcoes;
    private final HashSet<String> narrativasJaExibidas;
    private final String[] ultimasAcoes;
    private int indiceCircular;

    public Combate(Scanner scanner) {
        this.scanner = scanner;
        this.historicoAcoes = new ArrayList<>();
        this.narrativasJaExibidas = new HashSet<>();
        this.ultimasAcoes = new String[6];
        this.indiceCircular = 0;
    }

    public List<String> getHistoricoAcoes() {
        return Collections.unmodifiableList(historicoAcoes);
    }

    public boolean iniciar(PersonagemBase heroi, PersonagemBase inimigo) {
        PersonagemBase inimigoAtual = inimigo;
        boolean turnoJogador = true;
        boolean gokuJaInvocado = false;
        boolean chanceMeioVerificada = false;
        int turnosExecutados = 0;

        if (rolarEventoGoku()) {
            inimigoAtual = invocarGokuNoInicio(inimigoAtual);
            gokuJaInvocado = true;
        }

        registrarAcao("Um novo duelo comecou: " + heroi.getNome() + " vs " + inimigoAtual.getNome());

        while (heroi.estaVivo() && inimigoAtual.estaVivo()) {
            ConsoleUI.limparTela();
            ConsoleUI.mostrarBannerPrincipal();
            ConsoleUI.mostrarCampoBatalha(heroi, inimigoAtual);
            ConsoleUI.mostrarStatus(heroi, inimigoAtual);
            ConsoleUI.mostrarHistorico(getUltimasAcoesOrdenadas());

            if (turnoJogador) {
                executarTurnoJogador(heroi, inimigoAtual);
            } else {
                executarTurnoInimigo(inimigoAtual, heroi);
            }

            turnosExecutados++;

            if (heroi.estaVivo() && inimigoAtual.estaVivo()) {
                processarEventosDaBatalhaComGoku(heroi, inimigoAtual);
            }

            if (!gokuJaInvocado
                    && !chanceMeioVerificada
                    && turnosExecutados >= 2
                    && heroi.estaVivo()
                    && inimigoAtual.estaVivo()) {
                chanceMeioVerificada = true;
                if (rolarEventoGoku()) {
                    inimigoAtual = invocarGokuNoMeio(inimigoAtual);
                    gokuJaInvocado = true;
                    turnoJogador = false;
                    ConsoleUI.pausar(850);
                    continue;
                }
            }

            if (!gokuJaInvocado
                    && heroi.estaVivo()
                    && !inimigoAtual.estaVivo()
                    && rolarEventoGoku()) {
                inimigoAtual = invocarGokuNoFinal(inimigoAtual);
                gokuJaInvocado = true;
                turnoJogador = true;
                ConsoleUI.pausar(900);
                continue;
            }

            turnoJogador = !turnoJogador;
            ConsoleUI.pausar(850);
        }

        if (heroi.estaVivo()) {
            registrarAcao("Vitoria! " + inimigoAtual.getNome() + " foi derrotado.");
        } else {
            registrarAcao("Derrota... " + heroi.getNome() + " caiu em combate.");
        }

        ConsoleUI.limparTela();
        ConsoleUI.mostrarBannerPrincipal();
        ConsoleUI.mostrarStatus(heroi, inimigoAtual);
        ConsoleUI.mostrarHistorico(getUltimasAcoesOrdenadas());
        ConsoleUI.pausar(1300);
        return heroi.estaVivo();
    }

    private boolean rolarEventoGoku() {
        if (eventoGokuJaUsado) {
            return false;
        }

        boolean apareceu = ThreadLocalRandom.current().nextInt(100) < CHANCE_EVENTO_GOKU;
        if (apareceu) {
            eventoGokuJaUsado = true;
        }
        return apareceu;
    }

    private void exibirHistoriaAparicaoGoku(String momento, String nomeInimigo) {
        String inimigo = nomeInimigo == null || nomeInimigo.trim().isEmpty() ? "o inimigo" : nomeInimigo;

        ConsoleUI.limparTela();
        ConsoleUI.mostrarBannerPrincipal();
        ConsoleUI.mensagemAlerta("FENOMENO RARO (1%): uma ruptura de KI rasgou a arena.");
        ConsoleUI.mensagemInfo("Momento da anomalia: " + momento + ".");
        ConsoleUI.mensagemInfo("As correntes da dungeon tremem e o ar perde peso.");
        ConsoleUI.mensagemInfo("Isso pode acontecer em qualquer historia, em qualquer rota.");
        ConsoleUI.mensagemInfo("Nem " + inimigo + " consegue ignorar essa presenca.");
        ConsoleUI.mensagemErro("Goku Instinto Supremo atravessou a fenda e exigiu o duelo.");
        ConsoleUI.pausar(1400);
    }

    private PersonagemBase invocarGokuNoInicio(PersonagemBase inimigoOriginal) {
        String nomeInimigo = inimigoOriginal == null ? "o inimigo" : inimigoOriginal.getNome();
        exibirHistoriaAparicaoGoku("inicio da luta", nomeInimigo);
        ConsoleUI.mensagemAlerta("ALERTA DE KI: presenca desconhecida detectada.");
        ConsoleUI.mensagemErro("Goku apareceu antes da luta e tomou o lugar de " + nomeInimigo + ".");
        registrarAcao("Evento raro (1%): Goku invadiu o inicio do combate.");
        Goku goku = new Goku();
        ConsoleUI.mensagemInfo("Forma inicial detectada: " + goku.getNomeFormaAtual() + ".");
        return goku;
    }

    private PersonagemBase invocarGokuNoMeio(PersonagemBase inimigoAtual) {
        String nomeInimigo = inimigoAtual == null ? "o inimigo" : inimigoAtual.getNome();
        if (inimigoAtual != null && inimigoAtual.estaVivo()) {
            inimigoAtual.setVidaAtual(0);
        }

        exibirHistoriaAparicaoGoku("meio da luta", nomeInimigo);
        ConsoleUI.mensagemAlerta("RUPTURA DE COMBATE: energia esmagadora em aproximacao.");
        ConsoleUI.mensagemErro("Goku eliminou " + nomeInimigo + " e agora vai lutar com voce.");
        registrarAcao("Evento raro (1%): Goku surgiu no meio da luta e derrotou o inimigo atual.");
        Goku goku = new Goku();
        ConsoleUI.mensagemInfo("Forma inicial detectada: " + goku.getNomeFormaAtual() + ".");
        return goku;
    }

    private PersonagemBase invocarGokuNoFinal(PersonagemBase inimigoDerrotado) {
        String nomeInimigo = inimigoDerrotado == null ? "o inimigo" : inimigoDerrotado.getNome();
        exibirHistoriaAparicaoGoku("fim da luta", nomeInimigo);
        ConsoleUI.mensagemAlerta("VOCE MAL RESPIRA... e outra presenca aparece.");
        ConsoleUI.mensagemErro("Apos a queda de " + nomeInimigo + ", Goku entrou na arena para um novo duelo.");
        registrarAcao("Evento raro (1%): Goku apareceu no final do combate.");
        Goku goku = new Goku();
        ConsoleUI.mensagemInfo("Forma inicial detectada: " + goku.getNomeFormaAtual() + ".");
        return goku;
    }

    private void processarEventosDaBatalhaComGoku(PersonagemBase heroi, PersonagemBase inimigoAtual) {
        if (!(inimigoAtual instanceof Goku)) {
            processarNarrativaDeOutrosInimigos(heroi, inimigoAtual);
            return;
        }

        Goku goku = (Goku) inimigoAtual;
        processarTransformacaoDoGoku(heroi, goku);
        processarNarrativaDePressao(goku, heroi);
    }

    private void processarNarrativaDeOutrosInimigos(PersonagemBase heroi, PersonagemBase inimigoAtual) {
        List<String> eventosNarrativos = new ArrayList<String>();
        double vidaHeroi = heroi.getPercentualVida();
        double vidaInimigo = inimigoAtual.getPercentualVida();
        String nomeInimigo = inimigoAtual.getNome();

        if (inimigoAtual.getTipo() == TipoPersonagem.GOBLIN) {
            if (vidaInimigo <= 80.0) {
                eventosNarrativos.add(nomeInimigo + " ri com maldade: mesmo ferido, ele procura uma brecha na sua guarda.");
            }
            if (vidaHeroi <= 70.0) {
                eventosNarrativos.add(nomeInimigo + " se move mais rapido que o olhar e tenta te cansar no erro.");
            }
            if (vidaInimigo <= 45.0) {
                eventosNarrativos.add(nomeInimigo + " ja nao luta por bravura, mas por pura teimosia e medo de cair.");
            }
        } else if (inimigoAtual.getTipo() == TipoPersonagem.ORC) {
            if (vidaInimigo <= 85.0) {
                eventosNarrativos.add(nomeInimigo + " rosna alto. Quanto mais ferido, mais brutal fica seu ritmo de combate.");
            }
            if (vidaHeroi <= 60.0) {
                eventosNarrativos.add("Cada golpe de " + nomeInimigo + " parece um martelo. O impacto faz o chao vibrar sob seus pes.");
            }
            if (vidaInimigo <= 40.0) {
                eventosNarrativos.add("Mesmo sangrando, " + nomeInimigo + " avanca como se a dor apenas o alimentasse.");
            }
        } else if (inimigoAtual.getTipo() == TipoPersonagem.DRAGAO) {
            if (vidaInimigo <= 90.0) {
                eventosNarrativos.add("As escamas de " + nomeInimigo + " queimam em vermelho. Ele prepara um sopro ainda mais agressivo.");
            }
            if (vidaHeroi <= 65.0) {
                eventosNarrativos.add("O ar fica pesado. " + nomeInimigo + " pressiona voce como se quisesse dominar a arena inteira.");
            }
            if (vidaInimigo <= 35.0) {
                eventosNarrativos.add(nomeInimigo + " muda o tom da luta: agora cada movimento dele parece uma ultima investida.");
            }
        } else if (inimigoAtual.getTipo() == TipoPersonagem.CHEFE_IA
                || inimigoAtual.getTipo() == TipoPersonagem.CHEFE_PROVA
                || inimigoAtual.getTipo() == TipoPersonagem.CHEFE_MELANCIA
                || inimigoAtual.getTipo() == TipoPersonagem.CHEFE_VERDADE
                || inimigoAtual.getTipo() == TipoPersonagem.CHEFE_TROMPETE
                || inimigoAtual.getTipo() == TipoPersonagem.CHEFE_REI_DEMONIO) {
            if (vidaInimigo <= 85.0) {
                eventosNarrativos.add(nomeInimigo + " ajusta a postura. Ele percebe que o duelo deixou de ser simples.");
            }
            if (vidaHeroi <= 55.0) {
                eventosNarrativos.add("A presenca de " + nomeInimigo + " pesa no ar. O combate vira uma prova de resistencia mental.");
            }
            if (vidaInimigo <= 35.0) {
                eventosNarrativos.add(nomeInimigo + " esta encurralado, mas isso o torna ainda mais perigoso do que antes.");
            }
        }

        for (String evento : eventosNarrativos) {
            registrarNarrativa(evento);
            ConsoleUI.mensagemInfo(evento);
            ConsoleUI.pausar(850);
        }
    }

    private void processarTransformacaoDoGoku(PersonagemBase heroi, Goku goku) {
        String mensagemEvolucao = goku.tentarEvoluir(heroi);
        if (mensagemEvolucao == null) {
            return;
        }

        registrarAcao(mensagemEvolucao);
        ConsoleUI.mensagemAlerta(mensagemEvolucao);
        ConsoleUI.mensagemInfo("Forma ativa de Goku: " + goku.getNomeFormaAtual() + ".");
        if (goku.estaNaFormaFinal()) {
            String avisoFinal = "Goku alcancou sua forma final nesta batalha.";
            registrarAcao(avisoFinal);
            ConsoleUI.mensagemErro(avisoFinal);
        }
        ConsoleUI.pausar(1100);
    }

    private void processarNarrativaDePressao(Goku goku, PersonagemBase heroi) {
        List<String> eventosNarrativos = goku.coletarNarrativaDaBatalha(heroi);
        for (String evento : eventosNarrativos) {
            registrarNarrativa(evento);
            ConsoleUI.mensagemInfo(evento);
            ConsoleUI.pausar(900);
        }
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
        if (atacante == TipoPersonagem.GOKU && alvo != TipoPersonagem.GOKU) {
            return 1.45;
        }
        if (atacante != TipoPersonagem.GOKU && alvo == TipoPersonagem.GOKU) {
            return 0.75;
        }

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

    private void registrarNarrativa(String mensagem) {
        if (mensagem == null || mensagem.trim().isEmpty()) {
            return;
        }
        if (narrativasJaExibidas.add(mensagem)) {
            registrarAcao(mensagem);
        }
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
        int limiteVisual = Math.min(LIMITE_HISTORICO_VISUAL, ultimasAcoes.length);
        String[] ordenadas = new String[limiteVisual];
        HashSet<String> vistos = new HashSet<String>();
        int posicao = 0;

        for (int i = 0; i < ultimasAcoes.length && posicao < limiteVisual; i++) {
            int indice = (indiceCircular + i) % ultimasAcoes.length;
            String acao = ultimasAcoes[indice];
            if (acao != null && vistos.add(acao)) {
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
