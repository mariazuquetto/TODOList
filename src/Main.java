import model.Gerenciador;
import model.ItemMeta;
import view.FabricaVisualConcreta;
import view.IFabricaVisual;
import view.IMenuView;

import java.time.LocalDate;

public class Main {
    public static void main(String args[]) {
        Gerenciador programa = new Gerenciador();
        IFabricaVisual fabrica = FabricaVisualConcreta.getFabrica();
        IMenuView menu = fabrica.mostrarMenu(programa);
        menu.mostrar();

} }
