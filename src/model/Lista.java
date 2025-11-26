package model;
import java.util.ArrayList;

public abstract class Lista {
    private String nome;
    private ArrayList<Item> lista;

    public Lista(String nome) {
        setNome(nome);
        setLista(new ArrayList<Item>());
    }

    public void adicionarItem(Item item) {
        getLista().add(item);
    }

    public void excluirItem(Item item) {
        getLista().remove(item);
    }

    public Item getItem(int id) { return lista.get(id+1); }

    public abstract String descrever();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Item> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Item> lista) {
        this.lista = lista;
    }
}

