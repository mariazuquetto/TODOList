import model.Gerenciador;
import model.GerenciadorIO;
import view.FabricaVisualConcreta;
import view.IFabricaVisual;
import view.IMenuView;

public class Main {
    public static void main(String args[]) {
        Gerenciador programa = GerenciadorIO.desserializar();

        FabricaVisualConcreta.configurarInterface("GRAFICA"); // "GRAFICA" ou "TEXTUAL"
        IFabricaVisual fabrica = FabricaVisualConcreta.getFabrica();

        IMenuView menu = fabrica.mostrarMenu(programa);
        menu.mostrar();

} }
