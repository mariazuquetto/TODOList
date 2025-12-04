package model;

public enum Estado {
    PENDENTE,
    FEITO;

    @Override
    public String toString() {
        return "[" + this.name() + "]";
    }
}
