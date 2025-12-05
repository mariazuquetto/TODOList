package model;
import java.io.Serializable;
import java.time.LocalDate;

import static model.Utilidades.dataValida;

/**
 * Representa uma lista de tarefas (metas) com prazos definidos.
 * <p>
 * Estende a classe {@link Lista} para armazenar especificamente itens do tipo {@link ItemMeta}.
 * Esta lista é ideal para o planeamento de atividades que possuem uma data limite para conclusão.
 * </p>
 */
public class ListaMeta extends Lista<ItemMeta> {

    /**
     * Construtor para criar uma nova lista de tarefas (metas).
     *
     * @param nome O nome a atribuir à lista de tarefas.
     */
    public ListaMeta(String nome) {
        super(nome);
    }

    /**
     * Gera uma descrição textual da lista de tarefas.
     * <p>
     * Retorna uma string formatada contendo o tipo da lista ("Lista de Tarefas")
     * seguido pelo nome atribuído.
     * </p>
     *
     * @return Uma string descrevendo a lista.
     */
    @Override
    public String descrever() {
        return String.format("Lista de Tarefas: %s",
                getNome());
    }
}