package model;

import static model.Utilidades.stringValida;
import static model.Utilidades.valorValido;

public class ItemMidia extends Item {
    private String formatoMidia;
    private double nota;


    public ItemMidia(String titulo, String formatoMidia) {
        super(titulo);
        setFormatoMidia(formatoMidia);
        setNota(0);
    }

    @Override
    public String descrever() {
        if (getEstado() == Estado.PENDENTE) {
            return String.format("Mídia: %s | Formato: %s | Nota: %s/10 | Status: Pendente",
                    getTitulo(), formatoMidia, nota);
        } else {
            return String.format("Mídia: %s | Formato: %s | Nota: %s/10 | Status: Visto/Lido",
                    getTitulo(), formatoMidia, nota);
        }
    }

    public String getFormatoMidia() {
        return formatoMidia;
    }

    public void setFormatoMidia(String formatoMidia) throws IllegalArgumentException {
        if (stringValida(formatoMidia)) {
            this.formatoMidia = formatoMidia;
        } else {
            throw new IllegalArgumentException("Formato de mídia inválido.");
        }
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) throws IllegalArgumentException {
        if (nota >= 0 && nota <= 10) {
            this.nota = nota;
        } else {
            throw new IllegalArgumentException("Nota inválida. Insira uma nota entre 0 e 10.");
        }
    }
}
