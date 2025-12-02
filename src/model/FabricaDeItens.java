package model;

public class FabricaDeItens {
    public static ItemPadrao criarItemPadrao(String titulo) {
        return new ItemPadrao(titulo);
    }

    public static ItemMeta criarItemMeta(String titulo) {
        return new ItemMeta(titulo);
    }

    public static ItemCompra criarItemCompra(String titulo, int quantidade, double preco) {
        return new ItemCompra(titulo, quantidade, preco);
    }

    public static ItemMidia criarItemMidia(String titulo, String formatoMidia) {
        return new ItemMidia(titulo, formatoMidia);
    }
}

/// apagar
