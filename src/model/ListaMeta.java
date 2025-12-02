package model;
import java.time.LocalDate;

import static model.Utilidades.dataValida;

public class ListaMeta extends Lista<ItemMeta> {
    private LocalDate dataMeta;

    public ListaMeta(String nome) {
        super(nome);
        dataMeta = LocalDate.now();
    }

    @Override
    public String descrever() {
        return String.format("Lista de Tarefas: %s",
                getNome());
    }

    public LocalDate getDataMeta() {
        return dataMeta;
    }

    public void setDataMeta(LocalDate dataMeta) throws IllegalArgumentException {
        if (dataValida(dataMeta)) {
            this.dataMeta = dataMeta;
        } else {
            throw new IllegalArgumentException("Data inválida. Insira uma data no futuro.");
        }
    }
}