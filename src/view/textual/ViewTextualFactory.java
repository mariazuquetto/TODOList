package view.textual;

import model.Gerenciador;
import view.IFabricaVisual;
import view.IMenuView;

public class ViewTextualFactory implements IFabricaVisual {

    @Override
    public IMenuView mostrarMenu(Gerenciador gerenciador) {
        return new MenuTextualView(gerenciador);
    }
}
