package model;
import java.util.ArrayList;

public abstract class Lista {
    private String nome;
    private ArrayList<Item> lista;

    public Lista(String nome) {
        setNome(nome);
        setLista(new ArrayList<Item>());
    }

    public void adicionarItem(ItemPadrao item) {
        getLista().add(item);
    }

    public void excluirItem(ItemPadrao item) {
        getLista().remove(item);
    }

    public abstract void descrever();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList getLista() {
        return lista;
    }

    public void setLista(ArrayList lista) {
        this.lista = lista;
    }
}

