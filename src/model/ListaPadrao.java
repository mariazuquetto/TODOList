package model;
import java.time.LocalDate;

class ListaPadrao extends Lista {
    private LocalDate dataMeta;

    public ListaPadrao(String nome, LocalDate dataMeta) {
        super(nome);
        setDataMeta(dataMeta);
    }

    @Override
    public String descrever() {
        return String.format("Lista Padrão: %s",
                getNome());
    }

    public LocalDate getDataMeta() {
        return dataMeta;
    }

    public void setDataMeta(LocalDate dataMeta) {
        this.dataMeta = dataMeta;
    }
}