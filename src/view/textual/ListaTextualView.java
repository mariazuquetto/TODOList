package view.textual;

import model.*;
import view.IListaView;

import java.io.IOException;
import java.io.SyncFailedException;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static model.Utilidades.*;

/**
 * Implementação da interface de visualização de lista para o modo textual (consola).
 * <p>
 * Esta classe gere a interação do utilizador com uma lista específica através de menus de texto.
 * Permite listar itens, criar novos, editar existentes e remover itens, adaptando as
 * solicitações de dados (como datas, preços ou formatos) ao tipo específico de lista
 * que está a ser manipulada (Tarefas, Compras, Média, etc.).
 * </p>
 *
 * @param <S> O tipo de item contido na lista (subclasse de {@link Item}).
 * @param <T> O tipo da lista (subclasse de {@link Lista}).
 */
public class ListaTextualView <S extends Item, T extends Lista<S>> implements IListaView {

    private Gerenciador gerenciador;
    private T lista;
    private Scanner scanner;
    private boolean listaAtiva;

    /**
     * Construtor da vista textual de lista.
     * <p>
     * Inicializa o scanner para leitura da entrada padrão e define o estado da lista como ativa.
     * </p>
     *
     * @param gerenciador O gerenciador do sistema, usado para criar instâncias de itens.
     * @param lista A lista específica com a qual o utilizador irá interagir.
     */
    public ListaTextualView(Gerenciador gerenciador, T lista) {
        this.gerenciador = gerenciador;
        this.lista = lista;
        scanner = new Scanner(System.in);
        listaAtiva = true;
    }

    /**
     * Inicia o ciclo principal de interação com a lista.
     * <p>
     * Mantém o menu ativo, exibindo comandos e processando a entrada do utilizador
     * até que a opção de sair (0) seja selecionada.
     * </p>
     */
    @Override
    public void mostrar() {
        while (listaAtiva) {
            mostrarComandos();
            String comando = scanner.nextLine().trim();
            lidarComando(comando);
        }
    }

    /**
     * Exibe o menu de comandos disponíveis para a lista atual.
     * <p>
     * Mostra o cabeçalho com o nome da lista, os itens atuais, informações de rodapé
     * específicas (como totais de preços) e as opções numéricas de ação.
     * </p>
     */
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

    /**
     * Processa o comando numérico inserido pelo utilizador.
     *
     * @param comando A string contendo a opção escolhida ("1", "2", "3" ou "0").
     */
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

    /**
     * Lista todos os itens presentes na lista atual.
     * <p>
     * Exibe o ID (índice base-1) e a descrição de cada item. Caso a lista esteja vazia,
     * informa o utilizador.
     * </p>
     */
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

    /**
     * Exibe informações adicionais dependendo do tipo da lista.
     * <p>
     * Para {@link ListaCompra}, mostra a quantidade total de itens e o preço total acumulado.
     * Para {@link ListaMidia}, mostra o número de mídias concluídas e o total adicionado.
     * </p>
     */
    public void acaoDeLista() {
        if (lista instanceof ListaCompra) {
            System.out.println(String.format("Quantidade total: %-15d Preço total: R$%.2f",
                    ((ListaCompra) lista).quantidadeTotal(), ((ListaCompra) lista).precoTotal()));
        } else if (lista instanceof ListaMidia) {
            System.out.println("\nNúmero de mídias concluídas: " + ((ListaMidia) lista).getQuantidadeAvaliadas());
            System.out.println("Número de mídias totais adicionadas: " +((ListaMidia) lista).getQuantidadeTotal());
        }
    }

    /**
     * Inicia o fluxo de criação de um novo item.
     * <p>
     * Solicita o título e, dependendo do tipo da lista (Padrão, Meta, Compra, Mídia),
     * pede informações adicionais como data, preço, quantidade ou formato.
     * Trata exceções de formato e validação de dados.
     * </p>
     */
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

    /**
     * Inicia o fluxo de edição de um item existente.
     * <p>
     * Solicita o ID do item e apresenta um submenu com as propriedades editáveis
     * (Título, Estado, e campos específicos como Prazo, Quantidade, Nota).
     * </p>
     */
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
                        double nota = scanner.nextDouble();
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

    /**
     * Método auxiliar para solicitar e ler uma data do utilizador.
     *
     * @return A string da data inserida.
     */
    public String lidarData() {
        System.out.print("Insira o prazo do seu item (formato dd/mm/aaaa): ");
        String data = scanner.nextLine().trim();
        return data;
    }

    /**
     * Método auxiliar para solicitar e ler uma quantidade inteira.
     *
     * @return O valor inteiro da quantidade.
     * @throws IllegalArgumentException (Implicitamente via InputMismatchException do scanner) se a entrada não for um número.
     */
    public int lidarQuantidade() throws IllegalArgumentException {
        System.out.print("Insira a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();
        return quantidade;
    }

    /**
     * Método auxiliar para solicitar e ler um preço (double).
     *
     * @return O valor do preço.
     * @throws IllegalArgumentException (Implicitamente via InputMismatchException do scanner) se a entrada não for um número.
     */
    public double lidarPreco() throws IllegalArgumentException {
        System.out.print("Insira o preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        return preco;
    }

    /**
     * Método auxiliar para solicitar e ler o formato de mídia.
     *
     * @return A string do formato.
     */
    public String lidarFormato() {
        System.out.print("Insira o formato de mídia: ");
        String formato = scanner.nextLine().trim();
        return formato;
    }

    /**
     * Remove um item da lista após confirmação.
     * <p>
     * Solicita o ID do item a remover e pede confirmação (S/N).
     * Se confirmado, remove o item da coleção.
     * </p>
     */
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