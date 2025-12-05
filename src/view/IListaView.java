package view;

/**
 * Interface que define o contrato para a visualização de uma lista.
 * <p>
 * Esta interface é utilizada pelo padrão <i>Abstract Factory</i> (através da {@link IFabricaVisual})
 * para garantir que, independentemente da implementação concreta (Gráfica ou Textual),
 * todas as visualizações de listas possuam um método comum para serem exibidas ao utilizador.
 * </p>
 */
public interface IListaView {

    /**
     * Torna a visualização da lista visível ou ativa.
     * <p>
     * Nas implementações textuais, este método inicia o loop de interação com o utilizador.
     * Nas implementações gráficas, torna a janela visível.
     * </p>
     */
    public void mostrar();
}