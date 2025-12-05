package view;
import model.Gerenciador;
import model.Lista;

/**
 * Interface que define o contrato para a <i>Abstract Factory</i> de componentes visuais.
 * <p>
 * Este padrão permite a criação de famílias de objetos relacionados (neste caso, menus e visualizações de listas)
 * sem especificar as suas classes concretas. Isso possibilita que o sistema alterne facilmente
 * entre diferentes interfaces (como Gráfica ou Textual) mantendo a mesma lógica de negócio.
 * </p>
 */
public interface IFabricaVisual {

    /**
     * Cria e retorna a interface do Menu Principal adequada ao tipo de visualização atual.
     *
     * @param gerenciador O gerenciador do sistema, necessário para que o menu possa aceder e manipular as listas.
     * @return Uma instância de {@link IMenuView} (seja gráfica ou textual).
     */
    public IMenuView mostrarMenu(Gerenciador gerenciador);

    /**
     * Cria e retorna a interface de visualização para uma lista específica.
     * <p>
     * O método é genérico para suportar qualquer subclasse de {@link Lista} (Tarefas, Compras, Mídias, etc.),
     * garantindo que a visualização correta seja criada para o tipo de dados fornecido.
     * </p>
     *
     * @param gerenciador O gerenciador do sistema.
     * @param lista A lista que será exibida e manipulada na interface.
     * @param <T> O tipo específico da lista.
     * @return Uma instância de {@link IListaView} adequada ao tipo de lista e à interface selecionada.
     */
    public <T extends Lista> IListaView mostrarLista(Gerenciador gerenciador, T lista);
}