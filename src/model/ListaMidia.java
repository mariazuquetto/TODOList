package model;

import java.io.Serializable;

public class ListaMidia extends Lista<ItemMidia> {

    public ListaMidia(String nome) {
        super(nome);
    }

    public int getQuantidadeAvaliadas() {
        int avaliadas = 0;
        for (Item item : getLista()) {
            if (item.getEstado() == Estado.FEITO) {
                avaliadas += 1;
            }
        }
        return avaliadas;
    }

    public int getQuantidadeTotal() {
        return getLista().toArray().length;
    }

    @Override
    public String descrever() {
        return String.format("Lista de Mídias: %s",
                getNome());
    }
}
