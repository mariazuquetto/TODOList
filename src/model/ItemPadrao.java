package model;
import java.time.LocalDate;

public class ItemPadrao extends Item {
    private LocalDate dataMeta;

    public ItemPadrao(String titulo, LocalDate dataMeta) {
        super(titulo);
        setDataMeta(dataMeta);
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
        this.dataMeta = dataMeta;
    }
}

