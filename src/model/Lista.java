package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static model.Utilidades.stringValida;

public abstract class Lista<T extends Item> implements Serializable {
    private String nome;
    private List<T> lista;

    public Lista(String nome) {
        setNome(nome);
        setLista(new ArrayList<T>());
    }

    public void adicionarItem(T item) {
        getLista().add(item);
    }

    public void excluirItem(T item) {
        getLista().remove(item);
    }

    public T getItem(int id) { return lista.get(id-1); }

    public abstract String descrever();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws IllegalArgumentException {
        if (stringValida(nome)) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException("Nome da lista inválido.");
        }
    }

    public List<T> getLista() {
        return lista;
    }

    public void setLista(List<T> lista) {
        this.lista = lista;
    }
}

