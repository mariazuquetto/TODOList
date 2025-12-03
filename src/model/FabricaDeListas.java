package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class FabricaDeListas implements Serializable {
    private List<Lista> conjListas;
    private final int limiteListas = 10;

    public FabricaDeListas(List<Lista> conjListas) {
        this.conjListas = conjListas;
    }

    private void adicionarLista(Lista<?> novaLista) throws IllegalStateException {
        if (conjListas.size() >= limiteListas) {
            throw new IllegalStateException("O número máximo de listas (" + limiteListas + ") foi atingido. Não é possível criar mais listas.");
        }
        conjListas.add(novaLista);
    }

    public ListaPadrao criarListaPadrao(String nome) throws IllegalStateException {
        ListaPadrao lista = new ListaPadrao(nome);
        adicionarLista(lista);
        return lista;
    }

    public ListaCompra criarListaCompra(String nome) throws IllegalStateException {
        ListaCompra lista = new ListaCompra(nome);
        adicionarLista(lista);
        return lista;
    }

    public ListaMidia criarListaMidia(String nome) throws IllegalStateException {
        ListaMidia lista = new ListaMidia(nome);
        adicionarLista(lista);
        return lista;
    }

    public ListaMeta criarListaMeta(String nome) throws IllegalStateException {
        ListaMeta lista = new ListaMeta(nome);
        adicionarLista(lista);
        return lista;
    }
}
