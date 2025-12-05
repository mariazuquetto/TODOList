package model;
import java.time.LocalDate;

import static model.Utilidades.dataValida;

/**
 * Representa um item de meta (tarefa com prazo) numa lista.
 * <p>
 * Estende a classe {@link Item} adicionando um atributo de data limite (prazo).
 * Permite definir e validar a data de conclusão da tarefa.
 * </p>
 */
public class ItemMeta extends Item {
    private LocalDate dataMeta;

    /**
     * Construtor para criar um novo item de meta.
     *
     * @param titulo O título ou descrição da tarefa.
     * @param dataMeta A data limite para a conclusão da tarefa.
     */
    public ItemMeta(String titulo, LocalDate dataMeta) {
        super(titulo);
        this.dataMeta = dataMeta;
    }

    /**
     * Gera uma descrição textual detalhada do item de meta.
     * <p>
     * A string resultante inclui o título, a data limite formatada
     * e o estado atual do item.
     * </p>
     *
     * @return Uma string formatada com os detalhes da tarefa e o seu prazo.
     */
    @Override
    public String descrever() {
        String dataFormatada = Utilidades.formatarData(dataMeta);
        return String.format("%-25s até %s %s",
                getTitulo(),
                dataFormatada,
                getEstado().toString());
    }

    /**
     * Obtém a data limite definida para a tarefa.
     *
     * @return A data da meta (prazo).
     */
    public LocalDate getDataMeta() {
        return dataMeta;
    }

    /**
     * Define uma nova data limite para a tarefa.
     * <p>
     * A nova data é validada para garantir que não é anterior à data atual (hoje).
     * </p>
     *
     * @param dataMeta A nova data de prazo.
     * @throws IllegalArgumentException Se a data fornecida for inválida (anterior à data atual ou nula).
     */
    public void setDataMeta(LocalDate dataMeta) {
        if (dataValida(dataMeta)) {
            this.dataMeta = dataMeta;
        } else {
            throw new IllegalArgumentException("Data inválida. Insira uma data no futuro.");
        }
    }
}