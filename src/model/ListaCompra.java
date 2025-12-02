package model;

public class ListaCompra extends Lista<ItemCompra> {

    public ListaCompra(String nome) {
        super(nome);
    }

    public double precoTotal() {
        double precoTotal = 0;
        for (ItemCompra item : getLista()) {
            precoTotal += item.getPreco() * item.getQuantidade();
        }
        return precoTotal;
    }

    public int quantidadeTotal() {
        int quantidadeTotal = 0;
        for (ItemCompra item : getLista()) {
            quantidadeTotal += item.getQuantidade();
        }
        return quantidadeTotal;
    }

    @Override
    public String descrever() {
        return String.format("Lista de Compras: %s",
                getNome());
    }
}
