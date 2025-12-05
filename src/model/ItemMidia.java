package model;

import static model.Utilidades.stringValida;
import static model.Utilidades.valorValido;

/**
 * Representa um item de mídia (como filmes, jogos, livros, etc.) numa lista.
 * <p>
 * Estende a classe {@link Item} adicionando atributos específicos para o formato da mídia
 * (físico, digital, etc.) e uma nota de avaliação pessoal (0 a 10).
 * O estado de conclusão deste item reflete se a mídia foi "Visto/Lido" ou se ainda está "Pendente".
 * </p>
 */
public class ItemMidia extends Item {
    private String formatoMidia;
    private double nota;


    /**
     * Construtor para criar um novo item de mídia.
     * <p>
     * A nota de avaliação é iniciada automaticamente com o valor 0.
     * </p>
     *
     * @param titulo O título da obra (filme, jogo, livro, etc.).
     * @param formatoMidia O formato em que a mídia será consumida (ex: "DVD", "Digital", "Hardcover").
     * @throws IllegalArgumentException Se o formato fornecido for inválido.
     */
    public ItemMidia(String titulo, String formatoMidia) {
        super(titulo);
        setFormatoMidia(formatoMidia);
        setNota(0);
    }

    /**
     * Gera uma descrição textual detalhada do item de mídia.
     * <p>
     * A string resultante inclui o título, o formato, a nota atribuída e o status atual
     * ("Pendente" ou "Visto/Lido"), adaptando a mensagem conforme o estado do item.
     * </p>
     *
     * @return Uma string formatada com os detalhes da mídia.
     */
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

    /**
     * Obtém o formato da mídia definido.
     *
     * @return O formato (ex: "Digital", "Físico").
     */
    public String getFormatoMidia() {
        return formatoMidia;
    }

    /**
     * Define o formato da mídia.
     *
     * @param formatoMidia O novo formato a ser atribuído.
     * @throws IllegalArgumentException Se o formato for nulo ou vazio.
     */
    public void setFormatoMidia(String formatoMidia) throws IllegalArgumentException {
        if (stringValida(formatoMidia)) {
            this.formatoMidia = formatoMidia;
        } else {
            throw new IllegalArgumentException("Formato de mídia inválido.");
        }
    }

    /**
     * Obtém a nota de avaliação atribuída ao item.
     *
     * @return A nota atual (entre 0 e 10).
     */
    public double getNota() {
        return nota;
    }

    /**
     * Define uma nota de avaliação para a mídia.
     * <p>
     * A nota deve estar compreendida no intervalo de 0 a 10.
     * </p>
     *
     * @param nota O valor da nota a atribuir.
     * @throws IllegalArgumentException Se a nota for menor que 0 ou maior que 10.
     */
    public void setNota(double nota) throws IllegalArgumentException {
        if (nota >= 0 && nota <= 10) {
            this.nota = nota;
        } else {
            throw new IllegalArgumentException("Nota inválida. Insira uma nota entre 0 e 10.");
        }
    }
}