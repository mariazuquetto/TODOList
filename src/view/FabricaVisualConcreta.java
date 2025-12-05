package view;

import view.gui.ViewGraficaFactory;
import view.textual.ViewTextualFactory;

/**
 * Classe responsável pela configuração e fornecimento da fábrica de visualização concreta.
 * <p>
 * Esta classe gere a escolha dinâmica entre a interface gráfica e a interface textual.
 * Mantém uma instância estática de {@link IFabricaVisual} que é atualizada conforme a configuração
 * escolhida no início da execução do programa. Atua como um ponto central de acesso
 * para obter a fábrica correta sem que o restante do código precise saber qual tipo de interface está ativa.
 * </p>
 */
public class FabricaVisualConcreta {
    private static IFabricaVisual fabrica = new ViewGraficaFactory();

    /**
     * Configura o tipo de interface a ser utilizada pela aplicação.
     * <p>
     * Baseado na string fornecida ("GRAFICA" ou "TEXTUAL"), instancia a fábrica correspondente
     * ({@link ViewGraficaFactory} ou {@link ViewTextualFactory}) e define-a como a fábrica ativa.
     * </p>
     *
     * @param tipo O nome do tipo de interface desejada.
     * @throws IllegalArgumentException Se o tipo fornecido não corresponder a "GRAFICA" nem a "TEXTUAL".
     */
    public static void configurarInterface(String tipo) throws IllegalArgumentException {
        if (TipoInterface.GRAFICA.name().equalsIgnoreCase(tipo)) {
            fabrica = new ViewGraficaFactory();
        } else if (TipoInterface.TEXTUAL.name().equalsIgnoreCase(tipo)) {
            fabrica = new ViewTextualFactory();
        } else {
            throw new IllegalArgumentException("Tipo de interface inválida.");
        }
    }

    /**
     * Obtém a instância da fábrica visual atualmente configurada.
     *
     * @return A instância de {@link IFabricaVisual} pronta a ser utilizada para criar menus e visualizações.
     */
    public static IFabricaVisual getFabrica() {
        return fabrica;
    }
}

/**
 * Enumeração interna que define os tipos de interface suportados pelo sistema.
 */
enum TipoInterface {
    GRAFICA,
    TEXTUAL
}