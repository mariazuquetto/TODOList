package model;

public class ItemCompra extends Item {
    private int quantidade;
    private double preco;

    public ItemCompra(String titulo, int quantidade, double preco) {
        super(titulo);
        setQuantidade(quantidade);
        setPreco(preco);
    }

    @Override
    public String descrever() {
        double total = preco * quantidade;
        return String.format("%s %s(s) por %3s cada.\nTotal: %s %s",
                getQuantidade(),
                getTitulo(),
                preco,
                total,
                getEstado().toString());
    }

    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPreco() { return preco; }

    public void setPreco(double preco) { this.preco = preco; }
}
