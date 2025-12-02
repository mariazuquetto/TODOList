package model;
import java.time.LocalDate;

import static model.Utilidades.dataValida;

public class ItemMeta extends Item {
    private LocalDate dataMeta;

    public ItemMeta(String titulo) {
        super(titulo);
        dataMeta = LocalDate.now();
    }

    @Override
    public String descrever() {
        String dataFormatada = Utilidades.formatarData(dataMeta);
        return String.format("%-25s até %s %10s",
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

