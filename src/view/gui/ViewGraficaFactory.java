package view.gui;

import model.Gerenciador;
import model.Lista;
import view.IFabricaVisual;
import view.IListaView;
import view.IMenuView;

/**
 * Implementação concreta da fábrica de interfaces para a versão Gráfica (Swing).
 * <p>
 * Esta classe atua como uma <i>Concrete Factory</i> no padrão de projeto <i>Abstract Factory</i>.
 * É responsável por instanciar e retornar os componentes visuais gráficos da aplicação,
 * como o menu principal ({@link MenuGraficoView}) e as visualizações de listas ({@link ListaGraficaView}),
 * garantindo que toda a interface do utilizador seja consistente com o estilo gráfico.
 * </p>
 */
public class ViewGraficaFactory implements IFabricaVisual {
    public static ViewGraficaFactory instance = new ViewGraficaFactory();

    /**
     * Construtor padrão da fábrica gráfica.
     */
    public ViewGraficaFactory() {}

    /**
     * Cria e retorna a visualização do Menu Principal em modo gráfico.
     * <p>
     * Instancia uma nova janela {@link MenuGraficoView} que serve como ponto de entrada
     * para a interação do utilizador via interface gráfica.
     * </p>
     *
     * @param gerenciador O gerenciador do sistema contendo o estado atual das listas.
     * @return Uma instância de {@link IMenuView} (concretamente {@link MenuGraficoView}).
     */
    @Override
    public IMenuView mostrarMenu(Gerenciador gerenciador) {
        return new MenuGraficoView();
    }

    /**
     * Cria e retorna a visualização gráfica para uma lista específica.
     * <p>
     * Instancia uma janela {@link ListaGraficaView} configurada para exibir e manipular
     * os itens da lista fornecida. O uso de Generics {@code <T>} permite que este método
     * aceite qualquer tipo de lista (Tarefas, Compras, Mídia, etc.).
     * </p>
     *
     * @param gerenciador O gerenciador do sistema.
     * @param lista A lista específica que se pretende visualizar.
     * @param <T> O tipo específico da lista (subclasse de {@link Lista}).
     * @return Uma instância de {@link IListaView} (concretamente {@link ListaGraficaView}).
     */
    @Override
    public <T extends Lista> IListaView mostrarLista(Gerenciador gerenciador, T lista) {
        return new ListaGraficaView(gerenciador, lista);
    }
}