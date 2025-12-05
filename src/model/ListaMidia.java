package model;

import java.io.Serializable;

/**
 * Representa uma lista dedicada a itens de mídia (filmes, séries, jogos, livros, etc.).
 * <p>
 * Estende a classe {@link Lista} para armazenar especificamente itens do tipo {@link ItemMidia}.
 * Oferece funcionalidades adicionais para contabilizar o progresso do utilizador,
 * permitindo saber quantas mídias já foram consumidas (avaliadas/feitas) em relação ao total.
 * </p>
 */
public class ListaMidia extends Lista<ItemMidia> {

    /**
     * Construtor para criar uma nova lista de mídias.
     *
     * @param nome O nome a atribuir à lista de mídias.
     */
    public ListaMidia(String nome) {
        super(nome);
    }

    /**
     * Calcula a quantidade de mídias que já foram consumidas (marcadas como FEITO).
     * <p>
     * Percorre todos os itens da lista e conta aqueles cujo estado é {@link Estado#FEITO}.
     * Isso é útil para mostrar estatísticas de progresso ao utilizador.
     * </p>
     *
     * @return O número de mídias já avaliadas/concluídas.
     */
    public int getQuantidadeAvaliadas() {
        int avaliadas = 0;
        for (Item item : getLista()) {
            if (item.getEstado() == Estado.FEITO) {
                avaliadas += 1;
            }
        }
        return avaliadas;
    }

    /**
     * Obtém o número total de mídias adicionadas a esta lista.
     *
     * @return A quantidade total de itens na lista.
     */
    public int getQuantidadeTotal() {
        return getLista().toArray().length;
    }

    /**
     * Gera uma descrição textual da lista de mídias.
     * <p>
     * Retorna uma string formatada contendo o tipo da lista ("Lista de Mídias")
     * seguido pelo nome atribuído.
     * </p>
     *
     * @return Uma string descrevendo a lista.
     */
    @Override
    public String descrever() {
        return String.format("Lista de Mídias: %s",
                getNome());
    }
}