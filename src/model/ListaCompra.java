package model;

public class ListaCompra extends Lista {

    public ListaCompra(String nome) {
        super(nome);
    }

    public double precoTotal() {
        double precoTotal = 0;
        for (Item p : getLista()) {
            ItemCompra item = (ItemCompra) p;
            precoTotal += item.getPreco() * item.getQuantidade();
        }
        return precoTotal;
    }

    public int quantidadeTotal() {
        int quantidadeTotal = 0;
        for (Item p : getLista()) {
            ItemCompra item = (ItemCompra) p;
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
