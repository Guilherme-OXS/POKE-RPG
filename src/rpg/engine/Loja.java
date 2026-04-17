package rpg.engine;

import java.util.Scanner;

import rpg.model.PersonagemBase;
import rpg.model.itens.Elixir;
import rpg.model.itens.Item;
import rpg.model.itens.PocaoEnergia;
import rpg.model.itens.PocaoVida;
import rpg.utils.ConsoleUI;

public class Loja {
    private final Item[] catalogo;

    public Loja() {
        this.catalogo = new Item[] {
                new PocaoVida(),
                new PocaoEnergia(),
                new Elixir()
        };
    }

    public void abrir(PersonagemBase heroi, Scanner scanner) {
        boolean naLoja = true;

        while (naLoja) {
            ConsoleUI.limparTela();
            ConsoleUI.mostrarBannerPrincipal();
            ConsoleUI.mostrarLoja(heroi, catalogo);

            int opcao = lerOpcao(scanner, 0, catalogo.length);
            if (opcao == 0) {
                naLoja = false;
                continue;
            }

            try {
                Item escolhido = catalogo[opcao - 1];
                if (heroi.gastarOuro(escolhido.getPreco())) {
                    heroi.adicionarItem(escolhido.copiar());
                    ConsoleUI.mensagemSucesso("Compra concluida: " + escolhido.getNome());
                } else {
                    ConsoleUI.mensagemAlerta("Ouro insuficiente para essa compra.");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                ConsoleUI.mensagemErro("Opcao fora do catalogo. Tente novamente.");
            } catch (Exception e) {
                ConsoleUI.mensagemErro("Falha inesperada na loja: " + e.getMessage());
            } finally {
                ConsoleUI.pausar(700);
            }
        }
    }

    private int lerOpcao(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("Escolha: ");
            String texto = scanner.nextLine();
            try {
                int opcao = Integer.parseInt(texto.trim());
                if (opcao < min || opcao > max) {
                    throw new IllegalArgumentException("Opcao invalida");
                }
                return opcao;
            } catch (NumberFormatException e) {
                ConsoleUI.mensagemErro("Digite apenas numeros.");
            } catch (IllegalArgumentException e) {
                ConsoleUI.mensagemAlerta("Escolha entre " + min + " e " + max + ".");
            } finally {
                System.out.flush();
            }
        }
    }
}
