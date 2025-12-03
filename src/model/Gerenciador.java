package model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Gerenciador implements Serializable {
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

    public void excluirLista(int id) throws IndexOutOfBoundsException {
        conjListas.remove((id - 1));
    }

    public static ItemPadrao criarItemPadrao(String titulo) {
        return new ItemPadrao(titulo);
    }

    public static ItemMeta criarItemMeta(String titulo, LocalDate dataMeta) {
        return new ItemMeta(titulo, dataMeta);
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

    public static Gerenciador carregarEstado() {
        return GerenciadorIO.desserializar();
    }

    public void salvarEstado() throws IOException {
        GerenciadorIO.serializar(this);
    }
}