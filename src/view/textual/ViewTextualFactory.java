package view.textual;

import model.Gerenciador;
import model.Lista;
import view.IFabricaVisual;
import view.IListaView;
import view.IMenuView;

/**
 * Implementação concreta da fábrica de interfaces para a versão Textual (Consola).
 * <p>
 * Esta classe atua como uma <i>Concrete Factory</i> no padrão de projeto <i>Abstract Factory</i>.
 * É responsável por instanciar e retornar os componentes visuais textuais da aplicação,
 * que interagem com o utilizador através do terminal (System.in/System.out).
 * Cria o menu principal textual ({@link MenuTextualView}) e as visualizações de listas textuais ({@link ListaTextualView}).
 * </p>
 */
public class ViewTextualFactory implements IFabricaVisual {

    /**
     * Cria e retorna a visualização do Menu Principal em modo textual.
     * <p>
     * Instancia um objeto {@link MenuTextualView} que gere a interação inicial
     * do utilizador através da linha de comandos.
     * </p>
     *
     * @param gerenciador O gerenciador do sistema contendo o estado atual das listas.
     * @return Uma instância de {@link IMenuView} (concretamente {@link MenuTextualView}).
     */
    @Override
    public IMenuView mostrarMenu(Gerenciador gerenciador) {
        return new MenuTextualView();
    }

    /**
     * Cria e retorna a visualização textual para uma lista específica.
     * <p>
     * Instancia um objeto {@link ListaTextualView} configurado para exibir e manipular
     * os itens da lista fornecida via terminal. O uso de Generics {@code <T>} permite
     * suportar qualquer tipo de lista (Tarefas, Compras, etc.).
     * </p>
     *
     * @param gerenciador O gerenciador do sistema.
     * @param lista A lista específica que se pretende visualizar.
     * @param <T> O tipo específico da lista (subclasse de {@link Lista}).
     * @return Uma instância de {@link IListaView} (concretamente {@link ListaTextualView}).
     */
    @Override
    public <T extends Lista> IListaView mostrarLista(Gerenciador gerenciador, T lista) {
        return new ListaTextualView(gerenciador, lista);
    }
}