package model;

public class ListaMidia extends Lista<ItemMidia> {

    public ListaMidia(String nome) {
        super(nome);
    }

    public int getQuantidade() {
        return getLista().toArray().length;
    }

    @Override
    public String descrever() {
        return String.format("Lista de Mídias: %s",
                getNome());
    }
}
