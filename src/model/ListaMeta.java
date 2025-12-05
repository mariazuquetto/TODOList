package model;
import java.io.Serializable;
import java.time.LocalDate;

import static model.Utilidades.dataValida;

public class ListaMeta extends Lista<ItemMeta> {

    public ListaMeta(String nome) {
        super(nome);
    }

    @Override
    public String descrever() {
        return String.format("Lista de Tarefas: %s",
                getNome());
    }


}