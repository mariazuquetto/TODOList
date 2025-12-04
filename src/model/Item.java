package model;

import java.io.Serializable;

import static model.Utilidades.stringValida;

public abstract class Item implements Serializable  {
    private String titulo;
    private Estado estado;

    public Item(String titulo) {
        setTitulo(titulo);
        estado = Estado.PENDENTE;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) throws IllegalArgumentException {
        if (stringValida(titulo)) {
            this.titulo = titulo;
        } else {
            throw new IllegalArgumentException("Título inválido.");
        }
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public abstract String descrever();
}