package view.textual;

import model.Gerenciador;
import model.GerenciadorIO;
import model.Lista;
import model.ListaMeta;
import view.FabricaVisualConcreta;
import view.IFabricaVisual;
import view.IListaView;
import view.IMenuView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuTextualView <T extends Lista> implements IMenuView {
    private Gerenciador gerenciador;
    private Scanner scanner;
    private boolean menuAtivo;
    private boolean criarListaAtivo;

    public MenuTextualView() {
        gerenciador = Gerenciador.carregarEstado();
        scanner = new Scanner(System.in);
        menuAtivo = true;
    }

    @Override
    public void mostrar() {
        System.out.println("\nIniciando TODOList...");

        while (menuAtivo) {
            mostrarComandos();
            String comando = scanner.nextLine().trim();
            lidarComando(comando);
        }

        System.out.println("Fechando TODOList...");
    }

    public void mostrarComandos() {
        System.out.println("\n-------- MENU PRINCIPAL --------");
        System.out.println("Listas atuais:");
        mostrarListas();
        System.out.println("\nComandos:");
        System.out.println("1 - Abrir lista");
        System.out.println("2 - Criar nova lista");
        System.out.println("3 - Renomear lista");
        System.out.println("4 - Excluir lista");
        System.out.println("5 - Carregar arquivo");
        System.out.println("0 - Salvar e sair\n");
        System.out.print("Insira o valor comando: ");
    }

    public void lidarComando(String comando) {
        switch (comando) {
            case "1":
                abrirLista();
                break;
            case "2":
                criarListaAtivo = true;
                criarLista();
                break;
            case "3":
                renomearLista();
                break;
            case "4":
                excluirLista();
                break;
            case "5":
                try {
                    carregarArquivoTexto(scanner);
                } catch (Exception e) {
                    System.out.println("Erro ao carregar arquivo.");
                }
                break;
            case "0":
                menuAtivo = false;
                try {
                    gerenciador.salvarEstado();
                    System.out.println("\nSalvando...");}
                catch (IOException e) {
                    System.err.println("Erro ao salvar: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Comando desconhecido. Insira um número entre 0 e 4.");
        }
    }

    public void mostrarListas() {
        if (gerenciador.getConjListas().size() == 0) {
            System.out.println("Nenhuma lista no momento.");
        } else {
            int id = 0;
            for (Lista lista : gerenciador.getConjListas()) {
                id++;
                System.out.println(id + ". " + lista.descrever());
            }
        }
    }

    public void abrirLista() {
        System.out.print("Insira o ID da lista: ");
        try {
            int id = scanner.nextInt() - 1;
            scanner.nextLine();

            T lista = (T) gerenciador.getConjListas().get(id);

            IFabricaVisual fabrica = FabricaVisualConcreta.getFabrica();
            IListaView listaView = fabrica.mostrarLista(gerenciador, lista);

            System.out.println("Entrando na lista '" + lista.getNome() + "'.");
            listaView.mostrar();
            System.out.println("Saindo da lista '" + lista.getNome() + "'.");

        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Erro: ID inválido.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erro: ID inválido.");
        }
    }

    public void criarLista() {
        while (criarListaAtivo) {
            System.out.println("\n-------- TIPOS DE LISTA --------");
            System.out.println("1 - Lista Padrão");
            System.out.println("2 - Lista de Tarefas");
            System.out.println("3 - Lista de Compras");
            System.out.println("4 - Lista de Mídias");
            System.out.println("0 - Voltar ao Menu");
            System.out.print("\nInsira o valor do comando: ");
            String comando = scanner.nextLine().trim();
            try {
                switch (comando) {
                    case "1":
                        System.out.print("Insira o nome da sua lista: ");
                        String nome = scanner.nextLine().trim();
                        gerenciador.criarLista("ListaPadrao", nome);
                        System.out.print("Lista criada com sucesso.\n");
                        break;
                    case "2":
                        System.out.print("Insira o nome da sua lista: ");
                        nome = scanner.nextLine().trim();
                        gerenciador.criarLista("ListaMeta", nome);
                        System.out.print("Lista criada com sucesso.\n");
                        break;
                    case "3":
                        System.out.print("Insira o nome da sua lista: ");
                        nome = scanner.nextLine().trim();
                        gerenciador.criarLista("ListaCompra", nome);
                        System.out.print("Lista criada com sucesso.\n");
                        break;
                    case "4":
                        System.out.print("Insira o nome da sua lista: ");
                        nome = scanner.nextLine().trim();
                        gerenciador.criarLista("ListaMidia", nome);
                        System.out.print("Lista criada com sucesso.\n");
                        break;
                    case "0":
                        criarListaAtivo = false;
                        break;
                    default:
                        System.out.println("Comando desconhecido. Insira um número entre 0 e 4.");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    public void renomearLista() {
            try {
                System.out.print("Insira o ID da lista a ser renomeada: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Insira o novo nome da lista: ");
                String novoNome = scanner.nextLine().trim();

                gerenciador.atualizarLista(id, novoNome);
                criarListaAtivo = false;

            } catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("Erro: ID não corresponde a nenhuma lista.");
            } catch (IndexOutOfBoundsException e){
                System.out.println("Erro: ID não corresponde a nenhuma lista.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
        }
    }

    public void excluirLista() {
        try {
            System.out.print("Insira o ID da lista a ser excluída: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Realmente quer excluir '" + gerenciador.getConjListas().get(id-1).getNome() +"'? (S/N) ");
            String confirma = scanner.next().trim().toUpperCase();

            if (confirma.equals("S")) {
                gerenciador.excluirLista(id);
                scanner.nextLine();
                System.out.println("Lista excluída.");
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

    private void carregarArquivoTexto(Scanner sc) {
        System.out.print("Digite o caminho do arquivo (.ser): ");
        String caminho = sc.nextLine().trim();

        if (caminho.startsWith("\"") && caminho.endsWith("\"")) {
            caminho = caminho.substring(1, caminho.length() - 1);
        }

        try {
            Gerenciador novo = GerenciadorIO.carregarArquivo(caminho); // este método agora lança exceções quando incompatível
            this.gerenciador = novo;
            System.out.println("Arquivo carregado com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Verifique o caminho informado.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }
}