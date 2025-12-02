package model;

public class ItemPadrao extends Item {
    public ItemPadrao(String titulo) {
        super(titulo);
    }

    @Override
    public String descrever() {
        return String.format("%-25s %10s",
                getTitulo(),
                getEstado().toString());
    }
}
