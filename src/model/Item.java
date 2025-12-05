package model;

import java.io.Serializable;

import static model.Utilidades.stringValida;

/**
 * Classe abstrata que representa um item genérico numa lista.
 * <p>
 * Serve como base para todos os tipos específicos de itens (ex: {@link ItemPadrao}, {@link ItemMeta},
 * {@link ItemCompra}, {@link ItemMidia}). Define os atributos comuns, como o título e o estado
 * de conclusão, e impõe a implementação do método de descrição às subclasses.
 * </p>
 */
public abstract class Item implements Serializable  {
    private String titulo;
    private Estado estado;

    /**
     * Construtor base para um item.
     * Inicializa o item com um título e define o estado inicial como {@link Estado#PENDENTE}.
     *
     * @param titulo O título ou descrição principal do item.
     * @throws IllegalArgumentException Se o título fornecido for inválido (validado pelo setter).
     */
    public Item(String titulo) {
        setTitulo(titulo);
        estado = Estado.PENDENTE;
    }

    /**
     * Obtém o título do item.
     *
     * @return A string contendo o título do item.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define um novo título para o item.
     *
     * @param titulo O novo título a ser atribuído.
     * @throws IllegalArgumentException Se o título for nulo ou vazio.
     */
    public void setTitulo(String titulo) throws IllegalArgumentException {
        if (stringValida(titulo)) {
            this.titulo = titulo;
        } else {
            throw new IllegalArgumentException("Título inválido.");
        }
    }

    /**
     * Obtém o estado atual do item.
     *
     * @return O estado do item ({@link Estado#PENDENTE} ou {@link Estado#FEITO}).
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Define o estado de conclusão do item.
     *
     * @param estado O novo estado a atribuir ao item.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Método abstrato que deve retornar uma descrição textual detalhada do item.
     * As subclasses devem implementar este método para incluir informações específicas
     * do seu tipo (ex: data, preço, nota).
     *
     * @return Uma string formatada descrevendo o item.
     */
    public abstract String descrever();
}