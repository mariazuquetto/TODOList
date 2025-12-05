package model;

/**
 * Representa uma lista de itens padrão.
 * <p>
 * Estende a classe {@link Lista} para armazenar especificamente itens do tipo {@link ItemPadrao}.
 * É a implementação mais simples de uma lista, servindo para tarefas genéricas que não requerem
 * prazos, cálculos de preço ou avaliações de mídia.
 * </p>
 */
public class ListaPadrao extends Lista<ItemPadrao> {

    /**
     * Construtor para criar uma nova lista padrão.
     *
     * @param nome O nome a atribuir à lista.
     */
    public ListaPadrao(String nome) {
        super(nome);
    }

    /**
     * Gera uma descrição textual da lista padrão.
     * <p>
     * Retorna uma string formatada contendo o tipo da lista ("Lista Padrão")
     * seguido pelo nome atribuído.
     * </p>
     *
     * @return Uma string descrevendo a lista.
     */
    @Override
    public String descrever() {
        return String.format("Lista Padrão: %s",
                getNome());
    }
}