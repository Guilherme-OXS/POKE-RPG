package rpg;

import java.util.Scanner;

import rpg.engine.Dungeon;
import rpg.engine.Narrativa;
import rpg.model.PersonagemBase;
import rpg.model.herois.Arqueiro;
import rpg.model.herois.Guerreiro;
import rpg.model.herois.Mago;
import rpg.model.itens.PocaoEnergia;
import rpg.model.itens.PocaoVida;
import rpg.utils.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            iniciarJogo(scanner);
        } catch (Exception e) {
            ConsoleUI.digitarTexto("Erro critico no jogo: " + e.getMessage(), 10);
        } finally {
            ConsoleUI.digitarTexto("Encerrando sessao do RPG...", 10);
            scanner.close();
        }
    }

    private static void iniciarJogo(Scanner scanner) {
        ConsoleUI.limparTela();
        ConsoleUI.mostrarBannerPrincipal();
        ConsoleUI.mensagemInfo("Bem-vindo a Dungeon. Prepare-se para enfrentar o sistema.");
        ConsoleUI.pausar(700);

        PersonagemBase heroi = criarHeroi(scanner);
        Narrativa.RotaNarrativa rotaNarrativa = Narrativa.detectarRota(heroi.getNome());

        heroi.adicionarItem(new PocaoVida());
        heroi.adicionarItem(new PocaoEnergia());
        heroi.adicionarOuro(90);

        exibirCenaNarrativa(Narrativa.abertura(rotaNarrativa, heroi.getNome()), scanner);

        ConsoleUI.limparTela();
        ConsoleUI.mostrarBannerPrincipal();
        ConsoleUI.mensagemSucesso("Heroi criado: " + heroi.getNome());
        ConsoleUI.mensagemInfo("Classe escolhida: " + heroi.getTipo());
        ConsoleUI.mensagemInfo("Itens iniciais e ouro concedidos. A jornada vai comecar.");
        ConsoleUI.pausar(1000);

        Dungeon dungeon = new Dungeon(scanner, rotaNarrativa);
        boolean vitoria = dungeon.iniciar(heroi);

        ConsoleUI.limparTela();
        ConsoleUI.mostrarBannerPrincipal();
        if (vitoria) {
            exibirCenaNarrativa(Narrativa.epilogoVitoria(rotaNarrativa, heroi.getNome()), scanner);
            ConsoleUI.mensagemSucesso("PARABENS! Voce venceu toda a dungeon.");
        } else {
            exibirCenaNarrativa(Narrativa.epilogoDerrota(rotaNarrativa, heroi.getNome()), scanner);
            ConsoleUI.mensagemErro("Fim de jogo. O computador venceu desta vez.");
        }
    }

    private static void exibirCenaNarrativa(String[] linhas, Scanner scanner) {
        if (linhas == null || linhas.length == 0) {
            return;
        }

        ConsoleUI.limparTela();
        ConsoleUI.mostrarBannerPrincipal();
        for (int i = 0; i < linhas.length; i++) {
            ConsoleUI.mensagemInfo(linhas[i]);
            aguardarContinuacaoNarrativa(scanner);
        }
    }

    private static void aguardarContinuacaoNarrativa(Scanner scanner) {
        System.out.print("Pressione ENTER para continuar a historia... ");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Em caso de falha de entrada, evita quebrar o fluxo narrativo.
        } finally {
            System.out.flush();
        }
    }

    private static PersonagemBase criarHeroi(Scanner scanner) {
        String nome;
        while (true) {
            try {
                System.out.print("Digite o nome do heroi: ");
                nome = scanner.nextLine();
                if (nome == null || nome.trim().isEmpty()) {
                    throw new IllegalArgumentException("nome vazio");
                }
                break;
            } catch (IllegalArgumentException e) {
                ConsoleUI.mensagemErro("Nome invalido. Tente novamente.");
            } finally {
                System.out.flush();
            }
        }

        while (true) {
            ConsoleUI.mostrarPainelAcoes("ESCOLHA SUA CLASSE", new String[] {
                    "1. Guerreiro (alta vida e defesa)",
                    "2. Mago (alto dano e energia)",
                    "3. Arqueiro (equilibrio e precisao)"
            });

            System.out.print("Opcao: ");
            String entrada = scanner.nextLine();

            try {
                int opcao = Integer.parseInt(entrada.trim());
                switch (opcao) {
                    case 1:
                        return new Guerreiro(nome);
                    case 2:
                        return new Mago(nome);
                    case 3:
                        return new Arqueiro(nome);
                    default:
                        ConsoleUI.mensagemAlerta("Opcao invalida. Escolha entre 1 e 3.");
                        break;
                }
            } catch (NumberFormatException e) {
                ConsoleUI.mensagemErro("Entrada invalida. Digite um numero.");
            } finally {
                System.out.flush();
            }
        }
    }
}
