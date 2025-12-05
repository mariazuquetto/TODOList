package model;

/**
 * Enumeração que representa os estados possíveis de um item na lista de tarefas.
 * Define se um item está pendente ou se já foi concluído.
 */
public enum Estado {

    PENDENTE,
    FEITO;

    /**
     * Retorna uma representação em texto do estado, formatada entre parênteses retos.
     * <p>
     * Exemplo: {@code [PENDENTE]} ou {@code [FEITO]}.
     * </p>
     *
     * @return Uma string contendo o nome do estado entre parênteses retos.
     */
    @Override
    public String toString() {
        return "[" + this.name() + "]";
    }
}