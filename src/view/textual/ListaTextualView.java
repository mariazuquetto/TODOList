package view.textual;

import model.*;
import view.IListaView;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static model.Utilidades.*;

public class ListaTextualView <S extends Item, T extends Lista<S>> implements IListaView {

    private Gerenciador gerenciador;
    private T lista;
    private Scanner scanner;
    private boolean listaAtiva;

    public ListaTextualView(Gerenciador gerenciador, T lista) {
        this.gerenciador = gerenciador;
        this.lista = lista;
        scanner = new Scanner(System.in);
        listaAtiva = true;
    }

    @Override
    public void mostrar() {
        while (listaAtiva) {
            mostrarComandos();
            String comando = scanner.nextLine().trim();
            lidarComando(comando);
        }
    }

    public void mostrarComandos() {
        System.out.println("\n-------- "+ lista.descrever() +" --------");
        System.out.println("Itens atuais:");
        mostrarItens();
        acaoDeLista();
        System.out.println("\nComandos:");
        System.out.println("1 - Criar item");
        System.out.println("2 - Editar item");
        System.out.println("3 - Excluir item");
        System.out.println("0 - Sair da lista\n");
        System.out.print("Insira o valor comando: ");
    }

    public void lidarComando(String comando) {
        switch (comando) {
            case "1":
                criarItem();
                break;
            case "2":
                break;
            case "3":
                break;
            case "0":
                listaAtiva = false;
                break;
            default:
                System.out.println("Comando desconhecido. Insira um número entre 0 e 3.");
        }
    }

    public void mostrarItens() {
        if (lista.getLista().size() == 0) {
            System.out.println("Nenhuma lista no momento.");
        } else {
            int id = 0;
            for (S item : lista.getLista()) {
                id++;
                System.out.println(id + ". " + item.descrever());
            }
        }
    }

    public void acaoDeLista() {
        if (lista instanceof ListaCompra) {
            System.out.println(String.format("Quantidade total: %-15d Preço total: R$%.2f",
                    ((ListaCompra) lista).quantidadeTotal(), ((ListaCompra) lista).precoTotal()));
        } else if (lista instanceof ListaMidia) {
            System.out.println("Número de mídias avaliadas: " + ((ListaMidia) lista).getQuantidadeAvaliadas());
            System.out.println("Número de mídias totais adicionadas: " +((ListaMidia) lista).getQuantidadeTotal()+ "\n");
        }
    }

    public void criarItem() {
        try {
            System.out.print("Insira o título do seu item: ");
            String titulo = scanner.nextLine().trim();
            if (lista instanceof ListaPadrao) {
                S item = (S) gerenciador.criarItemPadrao(titulo);
                lista.adicionarItem(item);
                System.out.print("Item '" + item.getTitulo() + "' adicionado.\n");

            } else if (lista instanceof ListaMeta) {
                System.out.print("Insira o prazo do seu item (formato dd/mm/aaaa): ");
                String data = scanner.nextLine().trim();

                if (dataValida(formatarData(data))) {
                    S item = (S) gerenciador.criarItemMeta(titulo, formatarData(data));
                    lista.adicionarItem(item);
                    System.out.print("Item '" + item.getTitulo() + "' adicionado.\n");

                } else {
                    System.out.print("Data inválida. Insira uma data no futuro.\n");
                }

            } else if (lista instanceof ListaCompra) {
                System.out.print("Insira a quantidade: ");
                int quantidade = scanner.nextInt();
                scanner.nextLine();

                if (valorValido(quantidade)) {
                    System.out.print("Insira o preço: ");
                    double preco = scanner.nextDouble();
                    scanner.nextLine();

                    if (valorValido(preco)) {
                        S item = (S) gerenciador.criarItemCompra(titulo, quantidade, preco);
                        lista.adicionarItem(item);
                        System.out.print("Item '" + item.getTitulo() + "' adicionado.\n");

                    } else {
                        System.out.print("Preço inválido. Insira um valor maior ou igual a 0.\n");
                    }

                } else {
                    System.out.print("Quantidade inválida. Insira um valor inteiro maior que 0.\n");
                }

            } else if (lista instanceof ListaMidia) {
                System.out.print("Insira o formato de mídia: ");
                String formato = scanner.nextLine().trim();

                S item = (S) gerenciador.criarItemMidia(titulo, formato);
                lista.adicionarItem(item);
                System.out.print("Mídia '" + item.getTitulo() + "' adicionado.\n");

            } else {
                System.out.println("Tipo de lista desconhecido. Não é possível adicionar itens.\n");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Data fora do formato esperado.");
        }
    }
}