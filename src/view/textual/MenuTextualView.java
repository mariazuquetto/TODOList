package view.textual;

import model.Gerenciador;
import model.Lista;
import model.ListaMeta;
import view.IMenuView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuTextualView implements IMenuView {
    private Gerenciador gerenciador;
    private Scanner scanner;
    private boolean menuAtivo;
    private boolean criarListaAtivo;

    public MenuTextualView(Gerenciador gerenciador) {
        this.gerenciador = gerenciador;
        scanner = new Scanner(System.in);
        menuAtivo = true;
    }

    @Override
    public void mostrar() {
        System.out.println("Iniciando TODOList...");

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
        System.out.println("0 - Sair do programa\n");
        System.out.print("Insira o valor comando: ");
    }

    public void lidarComando(String comando) {
        switch (comando) {
            case "1":
                break;
            case "2":
                criarListaAtivo = true;
                criarLista();
                break;
            case "3":
                renomearLista();
                break;
            case "4":
                break;
            case "0":
                menuAtivo = false;
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

    public void criarLista() {
        while (criarListaAtivo) {
            System.out.println("\n-------- TIPOS DE LISTA --------");
            System.out.println("1 - Lista Padrão");
            System.out.println("2 - Lista com Prazo");
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
                        break;
                    case "2":
                        System.out.print("Insira o nome da sua lista: ");
                        nome = scanner.nextLine().trim();
                        gerenciador.criarLista("ListaMeta", nome);
                        break;
                    case "3":
                        System.out.print("Insira o nome da sua lista: ");
                        nome = scanner.nextLine().trim();
                        gerenciador.criarLista("ListaCompra", nome);
                        break;
                    case "4":
                        System.out.print("Insira o nome da sua lista: ");
                        nome = scanner.nextLine().trim();
                        gerenciador.criarLista("ListaMidia", nome);
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
                System.out.print("Insira o id da lista a ser renomeada: ");
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
}