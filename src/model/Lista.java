package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static model.Utilidades.stringValida;

/**
 * Classe abstrata que representa uma lista genérica de itens.
 * <p>
 * Define a estrutura base para todos os tipos de listas (como {@link ListaPadrao}, {@link ListaMeta},
 * {@link ListaCompra}, {@link ListaMidia}). Gerencia uma coleção de itens do tipo genérico {@code T}
 * e o nome da própria lista.
 * </p>
 * @param <T> O tipo de item que esta lista armazena, deve ser uma subclasse de {@link Item}.
 */
public abstract class Lista<T extends Item> implements Serializable {
    private String nome;
    private List<T> lista;

    /**
     * Construtor que inicializa a lista com um nome e cria uma coleção vazia de itens.
     *
     * @param nome O nome a ser atribuído à lista.
     * @throws IllegalArgumentException Se o nome fornecido for inválido.
     */
    public Lista(String nome) {
        setNome(nome);
        setLista(new ArrayList<T>());
    }

    /**
     * Adiciona um novo item à lista.
     *
     * @param item O item a ser adicionado.
     */
    public void adicionarItem(T item) {
        getLista().add(item);
    }

    /**
     * Remove um item específico da lista.
     *
     * @param item O item a ser removido.
     */
    public void excluirItem(T item) {
        getLista().remove(item);
    }

    /**
     * Obtém um item da lista com base no seu identificador numérico (ID).
     * <p>
     * O ID fornecido deve ser baseado em 1 (comum em interfaces de utilizador),
     * sendo convertido internamente para índice baseado em 0.
     * </p>
     *
     * @param id O identificador do item (1 a N).
     * @return O item correspondente ao ID.
     * @throws IndexOutOfBoundsException Se o ID for inválido (fora dos limites da lista).
     */
    public T getItem(int id) { return lista.get(id-1); }

    /**
     * Método abstrato para descrever a lista.
     * As subclasses devem implementar este método para retornar uma representação textual
     * adequada ao seu tipo (ex: "Lista de Compras: [Nome]").
     *
     * @return Uma string descrevendo a lista.
     */
    public abstract String descrever();

    /**
     * Obtém o nome da lista.
     *
     * @return O nome atual da lista.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define um novo nome para a lista.
     *
     * @param nome O novo nome a ser atribuído.
     * @throws IllegalArgumentException Se o nome for nulo ou vazio.
     */
    public void setNome(String nome) throws IllegalArgumentException {
        if (stringValida(nome)) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException("Nome da lista inválido.");
        }
    }

    /**
     * Obtém a coleção de itens armazenados na lista.
     *
     * @return A lista de itens.
     */
    public List<T> getLista() {
        return lista;
    }

    /**
     * Define a coleção de itens da lista.
     *
     * @param lista A nova lista de itens.
     */
    public void setLista(List<T> lista) {
        this.lista = lista;
    }
}