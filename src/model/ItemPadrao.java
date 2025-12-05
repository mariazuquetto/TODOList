package model;

/**
 * Representa um item padrão numa lista de tarefas.
 * <p>
 * Este é o tipo mais simples de item, contendo apenas um título e um estado (pendente ou feito),
 * sem atributos adicionais como prazos, preços ou notas.
 * Estende a classe {@link Item} e implementa a formatação básica para exibição.
 * </p>
 */
public class ItemPadrao extends Item {

    /**
     * Construtor para criar um novo item padrão.
     *
     * @param titulo O título ou descrição da tarefa.
     */
    public ItemPadrao(String titulo) {
        super(titulo);
    }

    /**
     * Gera uma descrição textual do item padrão.
     * <p>
     * A string resultante inclui o título formatado e alinhado à esquerda,
     * seguido pelo estado do item alinhado à direita.
     * </p>
     *
     * @return Uma string formatada com os detalhes do item.
     */
    @Override
    public String descrever() {
        return String.format("%-25s %10s",
                getTitulo(),
                getEstado().toString());
    }
}