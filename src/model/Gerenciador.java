package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Gerenciador {
    private List<Lista> conjListas;
    private FabricaDeListas fabricaDeListas;

    public Gerenciador() {
        conjListas = new ArrayList<>();
        fabricaDeListas = new FabricaDeListas(conjListas);
    }

    public void criarLista(String TIPO, String nome) {
        switch (TIPO) {
            case "ListaPadrao":
                fabricaDeListas.criarListaPadrao(nome);
                break;
            case "ListaMeta":
                fabricaDeListas.criarListaMeta(nome);
                break;
            case "ListaCompra":
                fabricaDeListas.criarListaCompra(nome);
                break;
            case "ListaMidia":
                fabricaDeListas.criarListaMidia(nome);
                break;
            default:
                throw new IllegalArgumentException("Tipo de lista inválida.");
        }
    }

    public boolean atualizarLista(int id, String nome) throws IllegalArgumentException {
        Lista listaEditar = conjListas.get(id-1);

        if (listaEditar != null) {
            listaEditar.setNome(nome);
            return true;
        }
        return false;
    }

    public boolean excluirLista(int id) {
        try {
            conjListas.remove(id - 1);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public static ItemPadrao criarItemPadrao(String titulo) {
        return new ItemPadrao(titulo);
    }

    public static ItemMeta criarItemMeta(String titulo) {
        return new ItemMeta(titulo);
    }

    public static ItemCompra criarItemCompra(String titulo, int quantidade, double preco) {
        return new ItemCompra(titulo, quantidade, preco);
    }

    public static ItemMidia criarItemMidia(String titulo, String formatoMidia) {
        return new ItemMidia(titulo, formatoMidia);
    }

    public List<Lista> getConjListas() {
        return conjListas;
    }


}