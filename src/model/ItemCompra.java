package model;

import java.io.Serializable;

import static model.Utilidades.valorValido;

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
        return String.format("%s %s(s) por R$%.2f cada.     Total: R$%.2f %s",
                getQuantidade(),
                getTitulo(),
                preco,
                total,
                getEstado().toString());
    }

    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) throws IllegalArgumentException {
        if (valorValido(quantidade)) {
            this.quantidade = quantidade;
        } else {
            throw new IllegalArgumentException("Valor inválido.");
        }
    }

    public double getPreco() { return preco; }

    public void setPreco(double preco) {
        if (valorValido(preco)) {
            this.preco = preco;
        } else {
            throw new IllegalArgumentException("Valor inválido.");
        }
    }
}
