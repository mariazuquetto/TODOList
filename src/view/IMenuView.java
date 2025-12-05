package view;

/**
 * Interface que define o contrato para a visualização do Menu Principal.
 * <p>
 * Esta interface é utilizada pelo padrão <i>Abstract Factory</i> (através da {@link IFabricaVisual})
 * para abstrair a implementação concreta do menu (seja gráfica ou textual).
 * Garante que o sistema possa iniciar a interação com o utilizador independentemente
 * do tipo de interface configurada.
 * </p>
 */
public interface IMenuView {

    /**
     * Exibe o menu principal ao utilizador.
     * <p>
     * Nas implementações textuais, este método inicia o ciclo de leitura de comandos no terminal.
     * Nas implementações gráficas, torna a janela do menu visível.
     * </p>
     */
    public void mostrar();
}