package model;
import java.time.LocalDate;

import static model.Utilidades.dataValida;

public class ItemMeta extends Item {
    private LocalDate dataMeta;

    public ItemMeta(String titulo, LocalDate dataMeta) {
        super(titulo);
        this.dataMeta = dataMeta;
    }

    @Override
    public String descrever() {
        String dataFormatada = Utilidades.formatarData(dataMeta);
        return String.format("%-25s até %s %s",
                getTitulo(),
                dataFormatada,
                getEstado().toString());
    }

    public LocalDate getDataMeta() {
        return dataMeta;
    }

    public void setDataMeta(LocalDate dataMeta) {
        if (dataValida(dataMeta)) {
            this.dataMeta = dataMeta;
        } else {
            throw new IllegalArgumentException("Data inválida. Insira uma data no futuro.");
        }
    }
}

