package view.textual;

import model.Gerenciador;
import model.Lista;
import view.IFabricaVisual;
import view.IListaView;
import view.IMenuView;

public class ViewTextualFactory implements IFabricaVisual {

    @Override
    public IMenuView mostrarMenu(Gerenciador gerenciador) {
        return new MenuTextualView();
    }

    @Override
    public <T extends Lista> IListaView mostrarLista(Gerenciador gerenciador, T lista) {
        return new ListaTextualView(gerenciador, lista);
    }
}
