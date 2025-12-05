import model.Gerenciador;
import model.GerenciadorIO;
import view.FabricaVisualConcreta;
import view.IFabricaVisual;
import view.IMenuView;

/**
 * Ponto de entrada (Entry Point) da aplicação TODOList.
 * <p>
 * Esta classe contém o método principal que inicializa o sistema, carrega os dados persistidos,
 * configura a interface de utilizador (gráfica ou textual) e inicia a exibição do menu principal.
 * </p>
 */
public class Main {

    /**
     * Método principal responsável por arrancar a aplicação.
     * <p>
     * O fluxo de execução é o seguinte:
     * 1. Recupera o estado anterior do sistema (listas e itens) através de {@link GerenciadorIO#desserializar()}.
     * 2. Configura a fábrica visual para utilizar a interface Gráfica (Swing).
     * 3. Obtém a instância da fábrica e cria o Menu Principal.
     * 4. Exibe o menu, transferindo o controlo para a camada de visualização.
     * </p>
     *
     * @param args Argumentos da linha de comandos (não utilizados nesta aplicação).
     */
    public static void main(String args[]) {
        Gerenciador programa = GerenciadorIO.desserializar();

        FabricaVisualConcreta.configurarInterface("TEXTUAL"); // "GRAFICA" ou "TEXTUAL"
        IFabricaVisual fabrica = FabricaVisualConcreta.getFabrica();

        IMenuView menu = fabrica.mostrarMenu(programa);
        menu.mostrar();

    } }