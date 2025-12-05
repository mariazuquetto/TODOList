package model;

import java.io.Serializable;

/**
 * Representa uma lista de compras específica.
 * <p>
 * Estende a classe {@link Lista} para armazenar especificamente itens do tipo {@link ItemCompra}.
 * Fornece funcionalidades adicionais para calcular o custo total e a quantidade total de itens na lista,
 * facilitando a gestão do orçamento e do planeamento de compras.
 * </p>
 */
public class ListaCompra extends Lista<ItemCompra> {

    /**
     * Construtor para criar uma nova lista de compras.
     *
     * @param nome O nome a atribuir à lista de compras.
     */
    public ListaCompra(String nome) {
        super(nome);
    }

    /**
     * Calcula o preço total de todos os itens presentes na lista.
     * <p>
     * O cálculo é feito somando o produto do preço pela quantidade de cada item.
     * </p>
     *
     * @return O valor monetário total da lista.
     */
    public double precoTotal() {
        double precoTotal = 0;
        for (ItemCompra item : getLista()) {
            precoTotal += item.getPreco() * item.getQuantidade();
        }
        return precoTotal;
    }

    /**
     * Calcula a quantidade total de itens (unidades) na lista.
     * <p>
     * Este método soma as quantidades individuais de todos os itens registados.
     * </p>
     *
     * @return O número total de unidades a comprar.
     */
    public int quantidadeTotal() {
        int quantidadeTotal = 0;
        for (ItemCompra item : getLista()) {
            quantidadeTotal += item.getQuantidade();
        }
        return quantidadeTotal;
    }

    /**
     * Gera uma descrição textual da lista de compras.
     * <p>
     * Retorna uma string formatada contendo o tipo da lista ("Lista de Compras")
     * seguido pelo nome atribuído.
     * </p>
     *
     * @return Uma string descrevendo a lista.
     */
    @Override
    public String descrever() {
        return String.format("Lista de Compras: %s",
                getNome());
    }
}