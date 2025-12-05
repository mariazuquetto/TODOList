package model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal de gestão (Controller) do sistema TODOList.
 * <p>
 * Esta classe atua como uma fachada (Facade) para as operações principais, mantendo
 * o estado global da aplicação (o conjunto de listas) e coordenando a criação,
 * atualização, exclusão e persistência de dados.
 * </p>
 * Implementa {@link Serializable} para permitir que o estado completo do gerenciador
 * seja salvo e recuperado em ficheiro.
 */
public class Gerenciador implements Serializable {
    private List<Lista> conjListas;
    private FabricaDeListas fabricaDeListas;

    /**
     * Construtor padrão do Gerenciador.
     * Inicializa a lista de listas e a fábrica associada.
     */
    public Gerenciador() {
        conjListas = new ArrayList<>();
        fabricaDeListas = new FabricaDeListas(conjListas);
    }

    /**
     * Cria uma nova lista do tipo especificado e adiciona-a ao sistema.
     *
     * @param TIPO O tipo de lista a criar (ex: "ListaPadrao", "ListaMeta", "ListaCompra", "ListaMidia").
     * @param nome O nome a atribuir à nova lista.
     * @throws IllegalArgumentException Se o tipo de lista fornecido não for reconhecido.
     * @throws IllegalStateException Se o limite máximo de listas tiver sido atingido (lançado pela fábrica).
     */
    public void criarLista(String TIPO, String nome) {
        switch (TIPO) {
            case "ListaPadrao":
                fabricaDeListas.criarListaPadrao(nome);
                break;
            case "ListaMeta":
                fabricaDeListas.criarListaMeta(nome);
                break;
            case "ListaCompra":
                fabricaDeListas.criarListaCompra(nome);
                break;
            case "ListaMidia":
                fabricaDeListas.criarListaMidia(nome);
                break;
            default:
                throw new IllegalArgumentException("Tipo de lista inválida.");
        }
    }

    /**
     * Renomeia uma lista existente identificada pelo seu ID.
     *
     * @param id O identificador numérico da lista (baseado em 1).
     * @param nome O novo nome para a lista.
     * @return {@code true} se a lista foi encontrada e atualizada com sucesso; {@code false} caso contrário.
     * @throws IllegalArgumentException Se o novo nome for inválido (validado internamente pela Lista).
     */
    public boolean atualizarLista(int id, String nome) throws IllegalArgumentException {
        // Ajusta o ID (utilizador usa base-1, lista usa base-0)
        Lista listaEditar = conjListas.get(id-1);

        if (listaEditar != null) {
            listaEditar.setNome(nome);
            return true;
        }
        return false;
    }

    /**
     * Exclui uma lista do sistema com base no seu ID.
     *
     * @param id O identificador numérico da lista a ser removida (baseado em 1).
     * @throws IndexOutOfBoundsException Se o ID fornecido não corresponder a nenhuma lista existente.
     */
    public void excluirLista(int id) throws IndexOutOfBoundsException {
        conjListas.remove((id - 1));
    }

    /**
     * Cria uma nova instância de um item padrão.
     *
     * @param titulo O título do item.
     * @return Um novo objeto {@link ItemPadrao}.
     */
    public static ItemPadrao criarItemPadrao(String titulo) {
        return new ItemPadrao(titulo);
    }

    /**
     * Cria uma nova instância de um item de meta (tarefa com prazo).
     *
     * @param titulo O título do item.
     * @param dataMeta A data limite para a conclusão da tarefa.
     * @return Um novo objeto {@link ItemMeta}.
     */
    public static ItemMeta criarItemMeta(String titulo, LocalDate dataMeta) {
        return new ItemMeta(titulo, dataMeta);
    }

    /**
     * Cria uma nova instância de um item de compra.
     *
     * @param titulo O nome do produto.
     * @param quantidade A quantidade a comprar.
     * @param preco O preço unitário do produto.
     * @return Um novo objeto {@link ItemCompra}.
     */
    public static ItemCompra criarItemCompra(String titulo, int quantidade, double preco) {
        return new ItemCompra(titulo, quantidade, preco);
    }

    /**
     * Cria uma nova instância de um item de mídia.
     *
     * @param titulo O título da mídia (filme, jogo, livro, etc.).
     * @param formatoMidia O formato da mídia (ex: "Digital", "Físico").
     * @return Um novo objeto {@link ItemMidia}.
     */
    public static ItemMidia criarItemMidia(String titulo, String formatoMidia) {
        return new ItemMidia(titulo, formatoMidia);
    }

    /**
     * Obtém a lista completa de listas geridas pelo sistema.
     *
     * @return A lista contendo todas as listas do utilizador.
     */
    public List<Lista> getConjListas() {
        return conjListas;
    }

    /**
     * Carrega o estado do gerenciador a partir do ficheiro de persistência padrão.
     *
     * @return Uma instância de {@link Gerenciador} com os dados recuperados, ou um novo gerenciador vazio se não houver dados.
     * @see GerenciadorIO#desserializar()
     */
    public static Gerenciador carregarEstado() {
        return GerenciadorIO.desserializar();
    }

    /**
     * Salva o estado atual do gerenciador no ficheiro de persistência padrão.
     *
     * @throws IOException Se ocorrer um erro durante a escrita do ficheiro.
     * @see GerenciadorIO#serializar(Gerenciador)
     */
    public void salvarEstado() throws IOException {
        GerenciadorIO.serializar(this);
    }
}