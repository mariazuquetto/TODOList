package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pela criação e gestão da quantidade de listas no sistema.
 * <p>
 * Implementa o padrão de projeto <i>Factory</i> (Fábrica) para centralizar a instanciação
 * dos diferentes tipos de listas (`ListaPadrao`, `ListaCompra`, etc.) e garantir
 * que o limite máximo de listas não seja ultrapassado.
 * </p>
 * Esta classe é final e não pode ser estendida.
 */
public final class FabricaDeListas implements Serializable {
    private List<Lista> conjListas;
    private final int limiteListas = 10;

    /**
     * Construtor da fábrica.
     *
     * @param conjListas A lista onde as novas listas criadas serão armazenadas.
     */
    public FabricaDeListas(List<Lista> conjListas) {
        this.conjListas = conjListas;
    }

    /**
     * Método auxiliar privado para adicionar uma lista à coleção.
     * Verifica se o limite máximo de listas foi atingido antes de adicionar.
     *
     * @param novaLista A nova lista a ser adicionada.
     * @throws IllegalStateException Se o número máximo de listas (10) já tiver sido atingido.
     */
    private void adicionarLista(Lista<?> novaLista) throws IllegalStateException {
        if (conjListas.size() >= limiteListas) {
            throw new IllegalStateException("O número máximo de listas (" + limiteListas + ") foi atingido. Não é possível criar mais listas.");
        }
        conjListas.add(novaLista);
    }

    /**
     * Cria e regista uma nova Lista Padrão.
     *
     * @param nome O nome da lista a ser criada.
     * @return A instância da {@link ListaPadrao} criada.
     * @throws IllegalStateException Se o limite de listas já tiver sido atingido.
     */
    public ListaPadrao criarListaPadrao(String nome) throws IllegalStateException {
        ListaPadrao lista = new ListaPadrao(nome);
        adicionarLista(lista);
        return lista;
    }

    /**
     * Cria e regista uma nova Lista de Compras.
     *
     * @param nome O nome da lista a ser criada.
     * @return A instância da {@link ListaCompra} criada.
     * @throws IllegalStateException Se o limite de listas já tiver sido atingido.
     */
    public ListaCompra criarListaCompra(String nome) throws IllegalStateException {
        ListaCompra lista = new ListaCompra(nome);
        adicionarLista(lista);
        return lista;
    }

    /**
     * Cria e regista uma nova Lista de Mídias.
     *
     * @param nome O nome da lista a ser criada.
     * @return A instância da {@link ListaMidia} criada.
     * @throws IllegalStateException Se o limite de listas já tiver sido atingido.
     */
    public ListaMidia criarListaMidia(String nome) throws IllegalStateException {
        ListaMidia lista = new ListaMidia(nome);
        adicionarLista(lista);
        return lista;
    }

    /**
     * Cria e regista uma nova Lista de Tarefas (Meta).
     *
     * @param nome O nome da lista a ser criada.
     * @return A instância da {@link ListaMeta} criada.
     * @throws IllegalStateException Se o limite de listas já tiver sido atingido.
     */
    public ListaMeta criarListaMeta(String nome) throws IllegalStateException {
        ListaMeta lista = new ListaMeta(nome);
        adicionarLista(lista);
        return lista;
    }
}