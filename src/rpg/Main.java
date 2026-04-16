package rpg;

import java.util.ArrayList;

/**
 * Classe principal que coordena o fluxo completo do jogo RPG.
 * 
 * Fluxo do jogo:
 * 1. Exibe logo, boas-vindas e carregamento
 * 2. Jogador seleciona classe (Guerreiro/Mago/Arqueiro)
 * 3. Cria lista de 5 fases com inimigos progressivos
 * 4. Para cada fase:
 *    a. Batalla se inicia com jogador vs inimigo
 *    b. Se vitória: ganha XP, ouro e pode subir nível
 *    c. Se fases restantes: abre loja para compras
 *    d. Se derrota: jogo termina
 * 5. Após vencer todas as fases: mensagem de vitória final
 * 6. Bloco finally garante mensagem de encerramento
 * 
 * Tratamento de exceção:
 * - Try: envolva todo o fluxo do jogo
 * - Catch: captura exceções inesperadas
 * - Finally: garante exibição de mensagem final
 * 
 * @author Projeto Integrador RPG
 * @see PersonagemBase
 * @see Inimigo
 * @see ConsoleUI
 */
public class Main {
    /**
     * Método principal que executa o jogo completo.
     * 
     * @param args Argumentos da linha de comando (não usados)
     */
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
            ArrayList<Inimigo> fases = criarTorreAleatoria();
            
            // Exibe mensagem da torre aleatória
            ConsoleUI.limparTela();
            ConsoleUI.printEffect("🗼 Torre de Desafios Aleatória!", ANSIColors.YELLOW);
            ConsoleUI.printEffect("Você enfrentará " + (fases.size() - 1) + " inimigos aleatórios...", ANSIColors.CYAN);
            ConsoleUI.printEffect("Seu objetivo final: DERROTAR O MONSTRO ABISSAL!", ANSIColors.RED);
            ConsoleUI.pressEnterToContinue();

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
            ConsoleUI.printEffect("🏆 🏆 🏆 VITÓRIA FINAL! 🏆 🏆 🏆", ANSIColors.GREEN);
            ConsoleUI.printEffect("Você derrotou o Monstro Abissal e salvou o reino!", ANSIColors.YELLOW);
            ConsoleUI.printEffect("Parabéns! Sua aventura foi extraordinária!", ANSIColors.GREEN);
            Animacoes.importantEffect("CRÉDITOS FINAIS");
            Sons.beepVictory();
        } catch (InterruptedException e) {
            // Thread.sleep foi interrompida
            System.out.println(ANSIColors.RED + "Jogo foi interrompido." + ANSIColors.RESET);
            Thread.currentThread().interrupt();
        } catch (IndexOutOfBoundsException e) {
            // Erro ao acessar ArrayList ou array
            System.out.println(ANSIColors.RED + "Erro ao carregar dados do jogo: " + e.getMessage() + ANSIColors.RESET);
        } catch (IllegalArgumentException e) {
            // Erro em argumentos inválidos
            System.out.println(ANSIColors.RED + "Erro de configuração: " + e.getMessage() + ANSIColors.RESET);
        } catch (Exception e) {
            // Qualquer outra exceção inesperada
            System.out.println(ANSIColors.RED + "Erro inesperado: " + e.getMessage() + ANSIColors.RESET);
            e.printStackTrace();
        } finally {
            // Bloco finally garantido: sempre executa
            ConsoleUI.printEffect("Obrigado por jogar! Reinicie para uma nova jornada.", ANSIColors.WHITE);
        }
    }

    /**
     * Menu de seleção de classe de personagem.
     * Apresenta as 3 opções: Guerreiro, Mago, Arqueiro.
     * Solicita nome do jogador (padrão: "Herói" se vazio).
     * 
     * Cria instância da classe selecionada com o nome fornecido.
     * 
     * @return Instância de PersonagemBase (Guerreiro, Mago ou Arqueiro)
     */
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

    /**
     * Cria uma "torre" de inimigos aleatórios até encontrar o BOSS final.
     * 
     * Sistema de Progressão:
     * - Começa com inimigos fracos (Goblin, Slime)
     * - Progressão aleatória entre vários tipos
     * - Cada vitória aproxima do Boss final
     * - Boss final aparece como última fase garantida
     * 
     * O jogador não sabe quantas fases há até enfrentar o BOSS!
     * Pode ser 3 fases ou 10 fases - totalmente aleatório.
     * 
     * @return ArrayList com inimigos aleatórios + Boss final no final
     */
    private static ArrayList<Inimigo> criarTorreAleatoria() {
        ArrayList<Inimigo> fases = new ArrayList<>();
        
        // Número aleatório de fases: entre 5 e 10 inimigos antes do Boss
        int numFases = 5 + (int)(Math.random() * 6);  // 5-10 inimigos
        
        // Cria inimigos aleatórios como "torres"
        for (int i = 0; i < numFases; i++) {
            fases.add(FabricaInimigos.criarInimigoAleatorio());
        }
        
        // Garante que o Boss final é o último inimigo
        fases.add(FabricaInimigos.criarMonstroFinal());
        
        return fases;
    }

    /**
     * Abre a loja entre fases para que o jogador possa comprar itens.
     * Disponível após cada fase (exceto a última).
     * 
     * Itens disponíveis:
     * 1. Poção de Vida (+30 HP) - 30 ouro
     * 2. Poção de Energia (+20 EN) - 25 ouro
     * 3. Espada (+5 ataque) - 45 ouro  
     * 4. Armadura (+5 defesa) - 45 ouro
     * 5. Amuleto (+10 energia máxima) - 35 ouro
     * 6. Sair da loja
     * 
     * Loop repetido até usuário escolher "Continuar".
     * Cada compra valida ouro suficiente antes de permitir.
     * Exibe mensagem de confirmação para cada compra.
     * 
     * @param jogador Personagem jogador disponível para compra
     */
    /**
     * Abre a loja entre fases com itens expandidos.
     * 
     * Itens disponíveis:
     * - Poções: Vida, Energia, Total
     * - Equipamentos: Escudo, Espada
     * - Buffs: Amuleto de Proteção, Elixir da Fúria
     * - Especiais: Antídoto, Mapa do Tesouro, Livro de Sabedoria
     * 
     * Jogador pode comprar múltiplos itens com seu ouro acumulado.
     * 
     * @param jogador Personagem do jogador
     */
    private static void abrirLoja(PersonagemBase jogador) {
        // Cria array de itens disponíveis no catálogo
        Item.TipoItem[] itensDisponiveis = {
                Item.TipoItem.POÇÃO_VIDA,
                Item.TipoItem.POÇÃO_ENERGIA,
                Item.TipoItem.POÇÃO_TOTAL,
                Item.TipoItem.ESCUDO_RESISTÊNCIA,
                Item.TipoItem.ESPADA_LETAL,
                Item.TipoItem.AMULETO_PROTEÇÃO,
                Item.TipoItem.ELIXIR_FURIA,
                Item.TipoItem.ANTÍDOTO,
                Item.TipoItem.MAPA_TESOURO,
                Item.TipoItem.LIVRO_SABEDORIA
        };
        
        // Cria strings de opção para menu
        String[] opcoes = new String[itensDisponiveis.length + 1];
        for (int i = 0; i < itensDisponiveis.length; i++) {
            Item.TipoItem tipo = itensDisponiveis[i];
            opcoes[i] = tipo.getNome() + " [" + tipo.getCusto() + "g]";
        }
        opcoes[itensDisponiveis.length] = "Sair da loja";
        
        // Loop da loja
        boolean continuar = false;
        while (!continuar) {
            ConsoleUI.limparTela();
            
            // Exibe informações da loja
            String[] conteudo = {
                    "🏪 Bem-vindo à Loja Mágica!",
                    "Ouro disponível: " + ANSIColors.YELLOW + jogador.getOuro() + ANSIColors.RESET + " ouro",
                    "Status atual: " + jogador.getDescricaoStatus(),
                    "",
                    "Escolha um item para comprar e melhorar suas chances de vitória!"
            };
            ConsoleUI.desenharCaixa("LOJA EXPANDIDA", conteudo, ConsoleUI.CAIXA_LARGURA, ANSIColors.PURPLE);
            System.out.println();
            
            ConsoleUI.mostrarMenu("Itens disponíveis:", opcoes);
            int escolha = ConsoleUI.lerOpcao(opcoes.length);
            
            // Se escolheu sair
            if (escolha == itensDisponiveis.length) {
                continuar = true;
            } else {
                // Tenta comprar o item
                Item.TipoItem tipoEscolhido = itensDisponiveis[escolha];
                
                if (jogador.gastarOuro(tipoEscolhido.getCusto())) {
                    // Compra bem-sucedida - usa o item
                    Item item = new Item(tipoEscolhido);
                    item.usar(jogador);
                    ConsoleUI.pressEnterToContinue();
                } else {
                    // Compra falhada - ouro insuficiente
                    ConsoleUI.printEffect("❌ Ouro insuficiente! Você precisa de " + 
                                        tipoEscolhido.getCusto() + " ouro.", ANSIColors.RED);
                    ConsoleUI.pressEnterToContinue();
                }
            }
        }
    }

    /**
     * Executa uma batalha entre jogador e inimigo.
     * 
     * Fluxo da batalha:
     * 1. Loop enquanto ambos estão vivos
     * 2. Renderiza tela de combate com HUD
     * 3. Jogador escolhe ação (Atacar/Habilidade/Recuperar)
     * 4. Valida e aplica ação do jogador
     * 5. Verifica vitória
     * 6. Inimigo executa ação automática (IA)
     * 7. Volta ao passo 2
     * 
     * Ações do Jogador:
     * - Atacar: 0 energia
     * - Habilidade Especial: consumo variar por classe
     * - Recuperar: restaura vida e energia (implementa Recuperavel)
     * 
     * Inimigo:
     * - Usa IA adaptativa com decidirAcao()
     * - Toma decisões baseadas em: vida/energia/situação
     * 
     * Condição de Vitória: Inimigo com vida <= 0  
     * Condição de Derrota: Jogador com vida <= 0
     * 
     * @param jogador Personagem do jogador
     * @param inimigo Inimigo a enfrentar
     * @param fase Número da fase (para exibição)
     * @return true se vitória (jogador vivo), false se derrota (jogador morto)
     * @throws InterruptedException Se Thread.sleep() for interrompida
     */
    /**
     * Executa uma batalha entre jogador e inimigo.
     * 
     * Fluxo da batalha:
     * 1. Loop enquanto ambos estão vivos
     * 2. Exibe HUD com stats e sprites
     * 3. Jogador escolhe ação (atacar, habilidade, recuperar, defender)
     * 4. Inimigo IA toma decisão
     * 5. Processa efeitos de status (dano passivo, duração)
     * 6. Verifica vitória/derrota
     * 
     * Status afetam cada turno com dano e modificadores.
     * 
     * @param jogador Personagem jogável
     * @param inimigo Inimigo na batalha
     * @param fase Número da fase atual
     * @return true se jogador venceu, false se perdeu
     * @throws InterruptedException Se thread for interrompida
     */
    private static boolean batalha(PersonagemBase jogador, Inimigo inimigo, int fase) throws InterruptedException {
        String[] jogadorSprite = SpritesASCII.getJogador();
        String[] inimigoSprite = inimigo.getSprite();
        String[] acoes = {"Atacar", "Habilidade Especial", "Recuperar", "Defender"};

        while (jogador.estaVivo() && inimigo.estaVivo()) {
            ConsoleUI.limparTela();
            ConsoleUI.renderTela(jogador, inimigo, inimigoSprite, jogadorSprite, "Fase " + fase, 
                                "Status: " + jogador.getDescricaoStatus());
            ConsoleUI.mostrarMenu(acoes);
            int escolha = ConsoleUI.lerOpcao(acoes.length);

            String resultado;
            switch (escolha) {
                case 0:  // Atacar
                    // Chance de falha por congelamento
                    if (Math.random() < jogador.getStatusManager().getProbabilidadeRecusa()) {
                        resultado = "CONGELADO! Você não consegue atacar...";
                    } else {
                        jogador.atacar(inimigo);
                        resultado = "Você usou Ataque.";
                    }
                    break;
                    
                case 1:  // Habilidade Especial
                    if (jogador instanceof HabilidadeEspecial) {
                        if (Math.random() < jogador.getStatusManager().getProbabilidadeRecusa()) {
                            resultado = "CONGELADO! Você não consegue usar habilidade...";
                        } else {
                            ((HabilidadeEspecial) jogador).usarHabilidade(inimigo);
                            resultado = "Você usou a habilidade especial.";
                        }
                    } else {
                        resultado = "Habilidade indisponível.";
                    }
                    break;
                    
                case 2:  // Recuperar
                    if (jogador instanceof Recuperavel) {
                        ((Recuperavel) jogador).recuperar();
                        resultado = "Você recuperou vida e energia.";
                    } else {
                        resultado = "Recuperação indisponível.";
                    }
                    break;
                    
                case 3:  // Defender
                    jogador.defender();
                    resultado = "Você assumiu postura defensiva!";
                    break;
                    
                default:
                    resultado = "Ação inválida.";
            }

            // Processa efeitos de status no jogador após sua ação
            jogador.procesarStatusTurno();
            
            Thread.sleep(Animacoes.DELAY_MEDIO);
            ConsoleUI.limparTela();
            ConsoleUI.renderTela(jogador, inimigo, inimigoSprite, jogadorSprite, "Fase " + fase, resultado);
            ConsoleUI.pressEnterToContinue();
            if (!inimigo.estaVivo()) {
                break;
            }

            // Turno do inimigo
            ConsoleUI.limparTela();
            ConsoleUI.renderTela(jogador, inimigo, inimigoSprite, jogadorSprite, "Fase " + fase, 
                                "Status inimigo: " + inimigo.getDescricaoStatus());
            ConsoleUI.printEffect("Inimigo contra-ataca...", ANSIColors.RED);
            Thread.sleep(Animacoes.DELAY_MEDIO);
            
            inimigo.decidirAcao(jogador);
            
            // Processa efeitos de status no inimigo após sua ação
            inimigo.procesarStatusTurno();
            
            Thread.sleep(Animacoes.DELAY_MEDIO);
            ConsoleUI.limparTela();
            ConsoleUI.renderTela(jogador, inimigo, inimigoSprite, jogadorSprite, "Fase " + fase, 
                                "O inimigo finalizou sua ação.");
            ConsoleUI.pressEnterToContinue();
        }
        
        // Encerra batalha e concede prêmios
        if (jogador.estaVivo()) {
            ConsoleUI.limparTela();
            Animacoes.actionAnimation("🎉 VITÓRIA!");
            ConsoleUI.printEffect("Você derrotou " + inimigo.getNome() + "!", ANSIColors.YELLOW);
            Thread.sleep(500);
            
            // Recompensas
            int xpGanho = 50;
            int ouroGanho = 25;
            jogador.ganharExperiencia(xpGanho);
            jogador.adicionarOuro(ouroGanho);
            ConsoleUI.printEffect("Ganhou " + xpGanho + " XP e " + ouroGanho + " ouro!", ANSIColors.GREEN);
            ConsoleUI.pressEnterToContinue();
        } else {
            ConsoleUI.limparTela();
            Animacoes.actionAnimation("💀 DERROTA!");
            ConsoleUI.printEffect("Você foi derrotado por " + inimigo.getNome() + ".", ANSIColors.RED);
            Thread.sleep(500);
        }
        
        return jogador.estaVivo();
    }
}
