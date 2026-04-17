package rpg.engine;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import rpg.model.PersonagemBase;
import rpg.model.itens.Elixir;
import rpg.model.itens.Item;
import rpg.model.itens.PocaoEnergia;
import rpg.model.itens.PocaoVida;
import rpg.model.inimigos.ChefeMisterioso;
import rpg.model.inimigos.Goblin;
import rpg.model.inimigos.Orc;
import rpg.utils.ConsoleUI;

public class Dungeon {
    private final Scanner scanner;
    private final Combate combate;
    private final Loja loja;
    private final PersonagemBase[] inimigos;
    private final Narrativa.RotaNarrativa rotaNarrativa;

    public Dungeon(Scanner scanner, Narrativa.RotaNarrativa rotaNarrativa) {
        this.scanner = scanner;
        this.combate = new Combate(scanner);
        this.loja = new Loja();
        this.rotaNarrativa = rotaNarrativa == null ? Narrativa.RotaNarrativa.PADRAO : rotaNarrativa;
        this.inimigos = criarSequenciaInimigos(this.rotaNarrativa);
    }

    public boolean iniciar(PersonagemBase heroi) {
        for (int fase = 0; fase < inimigos.length; fase++) {
            PersonagemBase inimigoAtual;

            try {
                inimigoAtual = inimigos[fase];
            } catch (ArrayIndexOutOfBoundsException e) {
                ConsoleUI.mensagemErro("Falha ao acessar inimigo da fase " + fase + ".");
                return false;
            }

            exibirCenaNarrativa(Narrativa.dialogoDaFase(rotaNarrativa, fase + 1, inimigoAtual.getNome()));

            if (fase == inimigos.length - 1) {
                exibirCenaNarrativa(Narrativa.antesDoChefe(rotaNarrativa));
            }

            ConsoleUI.mostrarTransicaoFase(fase + 1, inimigos.length, inimigoAtual.getNome());
            ConsoleUI.mensagemInfo("Fase " + (fase + 1) + " - " + inimigoAtual.getNome() + " apareceu!");
            ConsoleUI.pausar(800);

            boolean venceu = combate.iniciar(heroi, inimigoAtual);
            if (!venceu) {
                return false;
            }

            concederRecompensa(heroi, inimigoAtual);
            recuperarAposVitoria(heroi);

            if (fase == 1) {
                exibirCenaNarrativa(Narrativa.meioDaJornada(rotaNarrativa));
            }

            if ((fase + 1) % 2 == 0 && fase < inimigos.length - 1) {
                ConsoleUI.mensagemInfo("Um acampamento seguro foi encontrado.");
                ConsoleUI.pausar(600);
                loja.abrir(heroi, scanner);
            }
        }

        return true;
    }

    private PersonagemBase[] criarSequenciaInimigos(Narrativa.RotaNarrativa rota) {
        return new PersonagemBase[] {
                new Goblin("Skrik, o Ladrido"),
                new Orc("Brak, o Quebra-Escudo"),
                new Goblin("Nix, o Veloz"),
                new Orc("Urg, o Impiedoso"),
                criarChefeFinal(rota)
        };
    }

    private PersonagemBase criarChefeFinal(Narrativa.RotaNarrativa rota) {
        Narrativa.ChefeConfig chefe = Narrativa.criarChefeFinal(rota);
        return new ChefeMisterioso(
                chefe.getNome(),
            chefe.getTipo(),
                chefe.getHabilidade(),
                chefe.getAsciiArte(),
                chefe.getVidaMaxima(),
                chefe.getAtaque(),
                chefe.getDefesa(),
                chefe.getEnergiaMaxima(),
                chefe.getCustoHabilidade(),
                chefe.getMultiplicadorHabilidade(),
                chefe.getBonusHabilidade(),
                chefe.getCura(),
                chefe.getEnergiaRecuperada(),
                chefe.getTextoRecuperacao());
    }

    private void exibirCenaNarrativa(String[] linhas) {
        if (linhas == null || linhas.length == 0) {
            return;
        }

        ConsoleUI.limparTela();
        ConsoleUI.mostrarBannerPrincipal();
        for (int i = 0; i < linhas.length; i++) {
            ConsoleUI.mensagemInfo(linhas[i]);
            aguardarContinuacaoNarrativa();
        }
    }

    private void aguardarContinuacaoNarrativa() {
        System.out.print("Pressione ENTER para continuar a historia... ");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Mantem o jogo fluindo mesmo em caso de falha de leitura.
        } finally {
            System.out.flush();
        }
    }

    private void concederRecompensa(PersonagemBase heroi, PersonagemBase inimigo) {
        int ouroDrop = 20 + ThreadLocalRandom.current().nextInt(15, 41);
        heroi.adicionarOuro(ouroDrop);

        ConsoleUI.mensagemSucesso("Voce ganhou " + ouroDrop + " de ouro ao derrotar " + inimigo.getNome() + ".");

        int chanceDrop = ThreadLocalRandom.current().nextInt(100);
        if (chanceDrop < 55) {
            Item itemDropado = gerarDropAleatorio();
            heroi.adicionarItem(itemDropado);
            ConsoleUI.mensagemSucesso("Drop recebido: " + itemDropado.getNome());
        } else {
            ConsoleUI.mensagemAlerta("Nenhum item dropado nesta fase.");
        }

        ConsoleUI.pausar(900);
    }

    private Item gerarDropAleatorio() {
        int roll = ThreadLocalRandom.current().nextInt(100);

        if (roll < 45) {
            return new PocaoVida();
        }
        if (roll < 85) {
            return new PocaoEnergia();
        }
        return new Elixir();
    }

    private void recuperarAposVitoria(PersonagemBase heroi) {
        int cura = (int) Math.ceil(heroi.getVidaMaxima() * 0.20);
        int energia = (int) Math.ceil(heroi.getEnergiaMaxima() * 0.25);

        heroi.curarVida(cura);
        heroi.recuperarEnergia(energia);

        ConsoleUI.mensagemInfo("Descanso curto: +" + cura + " vida, +" + energia + " energia.");
    }
}
