package view.textual;

import model.Programa;
import view.IMenuView;

import java.util.Scanner;

public class MenuTextualView implements IMenuView {
    private Programa programa;
    private Scanner scanner;
    private boolean menuAtivo;

    public MenuTextualView(Programa programa) {
        this.programa = programa;
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
        System.out.println("\n----- MENU PRINCIPAL -----");
        System.out.println("Listas atuais:");
        mostrarListas();
        System.out.println("1 - Abrir lista");
        System.out.println("2 - Criar nova lista");
        System.out.println("3 - Renomear lista");
        System.out.println("4 - Excluir lista");
        System.out.println("0 - Sair do programa");
    }

    public void lidarComando(String comando) {
        switch (comando) {
            case "1":
                break;
            case "2":
                criarLista();
                break;
            case "3":
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


    }

    public void criarLista() {

    }
}
