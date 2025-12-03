package model;

import java.io.Serializable;

public class ListaPadrao extends Lista<ItemPadrao> {
    public ListaPadrao(String nome) {
        super(nome);
    }

    @Override
    public String descrever() {
        return String.format("Lista Padrão: %s",
                getNome());
    }
}
