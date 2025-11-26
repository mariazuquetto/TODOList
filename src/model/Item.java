package model;

public abstract class Item {
    private String titulo;
    private Estado estado;

    public Item(String titulo) {
        setTitulo(titulo);
        estado = Estado.NOVO;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public abstract String descrever();
}

enum Estado {
    NOVO,
    FEITO;

    @Override
    public String toString() {
        return "[" + this.name() + "]";
    }
}