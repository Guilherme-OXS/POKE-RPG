package rpg;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            ConsoleUI.limparTela();
            ConsoleUI.mostrarLogoEmpresa();
            ConsoleUI.limparTela();
            ConsoleUI.mostrarBemVindo();
            ConsoleUI.limparTela();
            ConsoleUI.mostrarCarregamento();
            ConsoleUI.limparTela();

            PersonagemBase jogador = selecionarPersonagem();
            ArrayList<Inimigo> fases = criarFases();

            for (int indice = 0; indice < fases.size(); indice++) {
                int fase = indice + 1;
                Inimigo atual = fases.get(indice);

                ConsoleUI.limparTela();
                ConsoleUI.printEffect("⚔️  Fase " + fase + " — " + atual.getNome(), ANSIColors.PURPLE);
                Thread.sleep(800);

                boolean vitoria = batalha(jogador, atual, fase);
                if (!vitoria) {
                    ConsoleUI.printEffect("💀 DERROTA! Sua jornada termina aqui.", ANSIColors.RED);
                    Sons.beepDefeat();
                    return;
                }

                int xpGanho = atual.getXpDrop();
                int ouroGanho = atual.getOuroDrop();
                jogador.adicionarOuro(ouroGanho);
                int niveis = jogador.ganharExperiencia(xpGanho);

                ConsoleUI.printEffect("🏆 Você derrotou " + atual.getNome() + "!", ANSIColors.GREEN);
                ConsoleUI.printEffect("Ganho: " + xpGanho + " XP e " + ouroGanho + " ouro.", ANSIColors.CYAN);
                if (niveis > 0) {
                    ConsoleUI.printEffect("🎉 Subiu para o nível " + jogador.getNivel() + "!", ANSIColors.GREEN);
                    ConsoleUI.printEffect("+5 Vida Máxima, +2 Ataque, +2 Defesa, +3 Energia.", ANSIColors.CYAN);
                }
                Thread.sleep(1000);

                if (fase < fases.size()) {
                    abrirLoja(jogador);
                }
            }

            ConsoleUI.limparTela();
            ConsoleUI.printEffect("🏆 VITÓRIA FINAL! Você salvou o reino e completou a aventura.", ANSIColors.GREEN);
            Sons.beepVictory();
        } catch (Exception e) {
            System.out.println(ANSIColors.RED + "Erro inesperado: " + e.getMessage() + ANSIColors.RESET);
        } finally {
            ConsoleUI.printEffect("Obrigado por jogar! Reinicie para uma nova jornada.", ANSIColors.WHITE);
        }
    }

    private static PersonagemBase selecionarPersonagem() {
        String[] classes = {"Guerreiro", "Mago", "Arqueiro"};
        ConsoleUI.limparTela();
        ConsoleUI.mostrarMenu("Escolha sua classe:", classes);
        int escolha = ConsoleUI.lerOpcao(classes.length);
        String nome = ConsoleUI.lerTexto("Digite o nome do herói: ");
        if (nome.isEmpty()) {
            nome = "Herói";
        }
        if (escolha == 0) {
            return new Guerreiro(nome);
        } else if (escolha == 1) {
            return new Mago(nome);
        } else {
            return new Arqueiro(nome);
        }
    }

    private static ArrayList<Inimigo> criarFases() {
        ArrayList<Inimigo> fases = new ArrayList<>();
        fases.add(new Inimigo("Goblin Sombrio", 80, 16, 7, 40, "Soco Sombrio", SpritesASCII.getGoblin(), 50, 25, 1));
        fases.add(new Inimigo("Esqueleto Errante", 95, 20, 9, 45, "Lâmina Óssea", SpritesASCII.getEsqueleto(), 70, 30, 2));
        fases.add(new Inimigo("Dragão Carmesim", 130, 26, 13, 55, "Sopro Flamejante", SpritesASCII.getDragao(), 100, 45, 3));
        fases.add(new Inimigo("Guerreiro Sombrio", 115, 24, 12, 50, "Investida Sombria", SpritesASCII.getGuerreiroSombrio(), 90, 40, 4));
        fases.add(new Inimigo("Soberano das Trevas", 170, 30, 16, 80, "Rajada Sombria", SpritesASCII.getBoss(), 140, 75, 5));
        return fases;
    }

    private static void abrirLoja(PersonagemBase jogador) {
        String[] opcoes = {
                "Poção de Vida (+30 HP) - 30 ouro",
                "Poção de Energia (+20 EN) - 25 ouro",
                "Espada (+5 ataque) - 45 ouro",
                "Armadura (+5 defesa) - 45 ouro",
                "Amuleto (+10 energia máxima) - 35 ouro",
                "Continuar a próxima fase"
        };
        boolean continuar = false;
        while (!continuar) {
            ConsoleUI.limparTela();
            String[] conteudo = {
                    "Bem-vindo à loja entre fases.",
                    "Ouro disponível: " + jogador.getOuro(),
                    "Escolha um item para reforçar sua jornada."
            };
            ConsoleUI.desenharCaixa("LOJA", conteudo, ConsoleUI.CAIXA_LARGURA, ANSIColors.PURPLE);
            System.out.println();
            ConsoleUI.mostrarMenu("Escolha um item:", opcoes);
            int escolha = ConsoleUI.lerOpcao(opcoes.length);
            boolean compraValida = true;
            switch (escolha) {
                case 0:
                    compraValida = jogador.gastarOuro(30);
                    if (compraValida) {
                        jogador.setVida(jogador.getVida() + 30);
                        ConsoleUI.printEffect("🍷 Poção de Vida comprada! HP recuperado.", ANSIColors.GREEN);
                    }
                    break;
                case 1:
                    compraValida = jogador.gastarOuro(25);
                    if (compraValida) {
                        jogador.setEnergia(jogador.getEnergia() + 20);
                        ConsoleUI.printEffect("✨ Poção de Energia comprada! Energia restaurada.", ANSIColors.GREEN);
                    }
                    break;
                case 2:
                    compraValida = jogador.gastarOuro(45);
                    if (compraValida) {
                        jogador.setAtaque(jogador.getAtaque() + 5);
                        ConsoleUI.printEffect("⚔️  Espada comprada! Ataque aumentado.", ANSIColors.GREEN);
                    }
                    break;
                case 3:
                    compraValida = jogador.gastarOuro(45);
                    if (compraValida) {
                        jogador.setDefesa(jogador.getDefesa() + 5);
                        ConsoleUI.printEffect("🛡️  Armadura comprada! Defesa aumentada.", ANSIColors.GREEN);
                    }
                    break;
                case 4:
                    compraValida = jogador.gastarOuro(35);
                    if (compraValida) {
                        jogador.setEnergiaMaxima(jogador.getEnergiaMaxima() + 10);
                        jogador.setEnergia(jogador.getEnergia() + 10);
                        ConsoleUI.printEffect("🔮 Amuleto comprado! Energia máxima aumentada.", ANSIColors.GREEN);
                    }
                    break;
                default:
                    continuar = true;
                    break;
            }
            if (!compraValida) {
                ConsoleUI.printEffect("Ouro insuficiente para essa compra.", ANSIColors.RED);
            }
            if (!continuar) {
                ConsoleUI.pressEnterToContinue();
            }
        }
    }

    private static boolean batalha(PersonagemBase jogador, Inimigo inimigo, int fase) throws InterruptedException {
        String[] jogadorSprite = SpritesASCII.getJogador();
        String[] inimigoSprite = inimigo.getSprite();
        String[] acoes = {"Atacar", "Habilidade Especial", "Recuperar"};

        while (jogador.estaVivo() && inimigo.estaVivo()) {
            ConsoleUI.limparTela();
            ConsoleUI.renderTela(jogador, inimigo, inimigoSprite, jogadorSprite, "Fase " + fase, "Selecione uma ação:");
            ConsoleUI.mostrarMenu(acoes);
            int escolha = ConsoleUI.lerOpcao(acoes.length);

            String resultado;
            switch (escolha) {
                case 0:
                    jogador.atacar(inimigo);
                    resultado = "Você usou Ataque.";
                    break;
                case 1:
                    if (jogador instanceof HabilidadeEspecial) {
                        ((HabilidadeEspecial) jogador).usarHabilidade(inimigo);
                        resultado = "Você usou a habilidade especial.";
                    } else {
                        resultado = "Habilidade indisponível.";
                    }
                    break;
                case 2:
                    if (jogador instanceof Recuperavel) {
                        ((Recuperavel) jogador).recuperar();
                        resultado = "Você recuperou vida e energia.";
                    } else {
                        resultado = "Recuperação indisponível.";
                    }
                    break;
                default:
                    resultado = "Ação inválida.";
            }

            Thread.sleep(Animacoes.DELAY_MEDIO);
            ConsoleUI.limparTela();
            ConsoleUI.renderTela(jogador, inimigo, inimigoSprite, jogadorSprite, "Fase " + fase, resultado);
            ConsoleUI.pressEnterToContinue();
            if (!inimigo.estaVivo()) {
                break;
            }

            ConsoleUI.limparTela();
            ConsoleUI.renderTela(jogador, inimigo, inimigoSprite, jogadorSprite, "Fase " + fase, "Inimigo contra-ataca...");
            Thread.sleep(Animacoes.DELAY_MEDIO);
            inimigo.decidirAcao(jogador);
            Thread.sleep(Animacoes.DELAY_MEDIO);
            ConsoleUI.limparTela();
            ConsoleUI.renderTela(jogador, inimigo, inimigoSprite, jogadorSprite, "Fase " + fase, "O inimigo finalizou sua ação.");
            ConsoleUI.pressEnterToContinue();
        }
        return jogador.estaVivo();
    }
}
