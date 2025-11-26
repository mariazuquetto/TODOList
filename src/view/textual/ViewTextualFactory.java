package view.textual;

import model.Programa;
import view.IFabricaVisual;
import view.IMenuView;

public class ViewTextualFactory implements IFabricaVisual {

    @Override
    public IMenuView mostrarMenu(Programa programa) {
        return new MenuTextualView(programa);
    }
}
