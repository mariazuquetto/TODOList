package view.textual;

import model.*;
import view.IListaView;

import java.io.IOException;
import java.io.SyncFailedException;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
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
                editarItem();
                break;
            case "3":
                excluirItem();
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
            System.out.println("Nenhum item no momento.");
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
            System.out.println("\nNúmero de mídias concluídas: " + ((ListaMidia) lista).getQuantidadeAvaliadas());
            System.out.println("Número de mídias totais adicionadas: " +((ListaMidia) lista).getQuantidadeTotal());
        }
    }

    public void criarItem() {
        try {
            System.out.print("Insira o título do seu item: ");
            String titulo = scanner.nextLine().trim();

            switch (lista.getClass().getSimpleName()) {
                case "ListaPadrao":
                    S item = (S) gerenciador.criarItemPadrao(titulo);
                    lista.adicionarItem(item);
                    System.out.print("Item '" + item.getTitulo() + "' adicionado.\n");
                    break;

                case "ListaMeta":
                    String data = lidarData();

                    if (dataValida(formatarData(data))) {
                        item = (S) gerenciador.criarItemMeta(titulo, formatarData(data));
                        lista.adicionarItem(item);
                        System.out.print("Item '" + item.getTitulo() + "' adicionado.\n");

                    } else {
                        System.out.print("Data inválida. Insira uma data no futuro.\n");
                    }
                    break;

                case "ListaCompra":
                    int quantidade = lidarQuantidade();

                    if (valorValido(quantidade)) {
                        double preco = lidarPreco();

                        if (valorValido(preco)) {
                            item = (S) gerenciador.criarItemCompra(titulo, quantidade, preco);
                            lista.adicionarItem(item);
                            System.out.print("Item '" + item.getTitulo() + "' adicionado.\n");
                        } else {
                            System.out.print("Preço inválido. Insira um valor maior ou igual a 0.\n");
                        }
                    } else {
                        System.out.print("Quantidade inválida. Insira um valor inteiro maior que 0.\n");
                    }
                    break;

                case "ListaMidia":
                    String formato = lidarFormato();

                    item = (S) gerenciador.criarItemMidia(titulo, formato);
                    lista.adicionarItem(item);
                    System.out.print("Mídia '" + item.getTitulo() + "' adicionado.\n");
                    break;

                default:
                    System.out.println("Tipo de lista desconhecido. Não é possível criar itens.\n");
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Data fora do formato esperado.");
        }
    }

    public void editarItem() {
        try {
            System.out.print("Insira o ID do item a editar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            String tipo = lista.getClass().getSimpleName();
            S item = lista.getItem(id);

            System.out.println("\n----- EDITAR: -----");
            System.out.println("1. Título");
            System.out.println("2. Estado");

            switch (tipo) {
                case "ListaMeta":
                    System.out.println("3. Prazo");
                    break;
                case "ListaCompra":
                    System.out.println("3. Quantidade");
                    System.out.println("4. Preço");
                    break;
                case "ListaMidia":
                    System.out.println("3. Formato de mídia");
                    System.out.println("4. Nota");
                    break;
                default:
                    break;
            }

            System.out.print("\nInsira o valor do comando: ");
            String comando = scanner.nextLine();

            switch (comando) {
                case "1":
                    System.out.print("Insira o título do seu item: ");
                    item.setTitulo(scanner.nextLine());
                    break;

                case "2":
                    System.out.print("Insira o estado do seu item (pendente/feito): ");
                    String estado = scanner.nextLine();
                    if (estado.equalsIgnoreCase("pendente")) {
                        item.setEstado(Estado.PENDENTE);
                    } else if (estado.equalsIgnoreCase("feito")) {
                        item.setEstado(Estado.FEITO);
                    } else {
                        System.out.println("Erro: Entrada inválida.\n");
                    }
                    break;

                case "3":
                    if (tipo.equals("ListaMeta")) {
                        String data = lidarData();
                        if (dataValida(formatarData(data))) {
                            ItemMeta itemMeta = (ItemMeta) item;
                            itemMeta.setDataMeta(formatarData(data));
                        } else {
                            System.out.println("Data inválida. Insira uma data no futuro.\n");
                        }

                    } else if (tipo.equals("ListaCompra")) {
                        int quantidade = lidarQuantidade();
                        if (valorValido(quantidade)) {
                            ItemCompra itemCompra = (ItemCompra) item;
                            itemCompra.setQuantidade(quantidade);
                        } else {
                            System.out.println("Valor inválido. Insira um valor maior que 0.\n");
                        }

                    } else if (tipo.equals("ListaMidia")) {
                        String formato = lidarFormato();
                        if (stringValida(formato)) {
                            ItemMidia itemMidia = (ItemMidia) item;
                            ((ItemMidia) item).setFormatoMidia(formato);
                        }
                    } else {
                        System.out.println("Comando desconhecido.");
                    }
                    break;

                case "4":
                    if (tipo.equals("ListaCompra")) {
                        double preco = lidarPreco();
                        if (valorValido(preco)) {
                            ItemCompra itemCompra = (ItemCompra) item;
                            itemCompra.setPreco(preco);
                        } else {
                            System.out.println("Valor inválido. Insira um valor maior ou igual a 0.\n");
                        }

                    } else if (tipo.equals("ListaMidia")) {
                        System.out.print("Insira a nota: ");
                        double nota = scanner.nextInt();
                        scanner.nextLine();

                        ItemMidia itemMidia = (ItemMidia) item;
                        ((ItemMidia) item).setNota(nota);

                    } else {
                        System.out.println("Comando desconhecido.");
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Comando desconhecido.");
            }

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Erro: Entrada inválida.");
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Data fora do formato esperado.");
        }
    }

    public String lidarData() {
        System.out.print("Insira o prazo do seu item (formato dd/mm/aaaa): ");
        String data = scanner.nextLine().trim();
        return data;
    }

    public int lidarQuantidade() throws IllegalArgumentException {
        System.out.print("Insira a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();
        return quantidade;
    }

    public double lidarPreco() throws IllegalArgumentException {
        System.out.print("Insira o preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        return preco;
    }

    public String lidarFormato() {
        System.out.print("Insira o formato de mídia: ");
        String formato = scanner.nextLine().trim();
        return formato;
    }

    public void excluirItem() {
        try {
            System.out.print("Insira o ID do item a ser excluído: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Realmente quer excluir '" + lista.getItem(id).getTitulo() +"'? (S/N) ");
            String confirma = scanner.next().trim().toUpperCase();

            if (confirma.equals("S")) {
                lista.getLista().remove(lista.getItem(id));
                scanner.nextLine();
                System.out.println("Item excluído.");
            } else {
                scanner.nextLine();
                System.out.println("Operação cancelada.");
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Erro: ID inválido.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erro: ID inválido.");
        }
    }
}