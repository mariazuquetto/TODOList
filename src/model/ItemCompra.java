package model;

import java.io.Serializable;

import static model.Utilidades.valorValido;

/**
 * Representa um item específico para listas de compras.
 * <p>
 * Estende a classe {@link Item} adicionando atributos para quantidade e preço unitário.
 * É capaz de calcular o preço total do item com base nestes valores.
 * </p>
 */
public class ItemCompra extends Item {
    private int quantidade;
    private double preco;

    /**
     * Construtor para criar um novo item de compra.
     *
     * @param titulo O nome do produto a comprar.
     * @param quantidade A quantidade de unidades a comprar.
     * @param preco O preço unitário do produto.
     * @throws IllegalArgumentException Se a quantidade ou o preço forem inválidos.
     */
    public ItemCompra(String titulo, int quantidade, double preco) {
        super(titulo);
        setQuantidade(quantidade);
        setPreco(preco);
    }

    /**
     * Gera uma descrição textual detalhada do item de compra.
     * <p>
     * A string resultante inclui a quantidade, o título, o preço unitário,
     * o preço total (quantidade * preço) e o estado atual do item.
     * </p>
     *
     * @return Uma string formatada com os detalhes da compra.
     */
    @Override
    public String descrever() {
        double total = preco * quantidade;
        return String.format("%s %s(s) por R$%.2f cada.     Total: R$%.2f %s",
                getQuantidade(),
                getTitulo(),
                preco,
                total,
                getEstado().toString());
    }

    /**
     * Obtém a quantidade de itens a comprar.
     *
     * @return O número de unidades.
     */
    public int getQuantidade() { return quantidade; }

    /**
     * Define a quantidade de itens.
     *
     * @param quantidade O novo valor da quantidade.
     * @throws IllegalArgumentException Se a quantidade for menor ou igual a zero.
     */
    public void setQuantidade(int quantidade) throws IllegalArgumentException {
        if (valorValido(quantidade)) {
            this.quantidade = quantidade;
        } else {
            throw new IllegalArgumentException("Valor inválido.");
        }
    }

    /**
     * Obtém o preço unitário do item.
     *
     * @return O valor do preço.
     */
    public double getPreco() { return preco; }

    /**
     * Define o preço unitário do item.
     *
     * @param preco O novo valor do preço.
     * @throws IllegalArgumentException Se o preço for negativo.
     */
    public void setPreco(double preco) {
        if (valorValido(preco)) {
            this.preco = preco;
        } else {
            throw new IllegalArgumentException("Valor inválido.");
        }
    }
}